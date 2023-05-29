package com.webpower.myapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webpower.myapp.common.ResultResponse;
import com.webpower.myapp.config.Constants;
import com.webpower.myapp.domain.Material;
import com.webpower.myapp.domain.SplitScreen;
import com.webpower.myapp.model.MaterialSplitScreenModel;
import com.webpower.myapp.repository.MaterialRepository;
import com.webpower.myapp.service.MaterialService;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;

/**
 * Service Implementation for managing {@link Material}.
 *
 * @author Lt'x
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MaterialServiceImpl implements MaterialService {

    private final Logger log = LoggerFactory.getLogger(MaterialServiceImpl.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final MaterialRepository materialRepository;


    private final SplitScreenServiceImpl splitScreenService;

    public MaterialServiceImpl(MaterialRepository materialRepository,
                               SplitScreenServiceImpl splitScreenService) {
        this.materialRepository = materialRepository;
        this.splitScreenService = splitScreenService;
    }


    /**
     * 获取素材分页列表
     *
     * @param pageable 分页信息
     * @param keyword  搜索关键词
     * @return 素材分页列表
     */
    @Transactional(readOnly = true)
    @Override
    public ResultResponse<Page<Material>> findMaterialListByNameOrCreateBy(Pageable pageable, String keyword) {
        ResultResponse<Page<Material>> serviceResult = ResultResponse.newSuccessInstance();
        try {
            log.info("trace:{},MaterialService-findMaterialListByNameOrCreateBy-参数:{},{}", serviceResult.getTrace(),
                objectMapper.writeValueAsString(pageable), keyword);

            Page<Material> materialPageList = materialRepository.findAll((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(keyword)) {
                    predicates.add(criteriaBuilder.or(criteriaBuilder.like(root.get("materialName"), "%" + keyword + "%"),
                        criteriaBuilder.like(root.get("createName"), "%" + keyword + "%"))
                    );
                }
                predicates.add(criteriaBuilder.equal(root.get("delFlag"), 0));
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }, pageable);
            serviceResult.succeed().message("获取成功！").data(materialPageList);
        } catch (Exception e) {
            log.error("获取素材分页列表失败！", e);
            serviceResult.failed().code(HttpStatus.INTERNAL_SERVER_ERROR).message("获取素材分页列表失败");
        }
        return serviceResult;
    }


    /**
     * 根据id获取彩信素材和分屏预览
     *
     * @param id 彩信
     * @return 彩信素材和分屏
     */
    @Transactional(readOnly = true)
    @Override
    public ResultResponse<MaterialSplitScreenModel> findMaterialSplitScreenById(Long id) {
        ResultResponse<MaterialSplitScreenModel> serviceResult = ResultResponse.newSuccessInstance();
        log.info("trace:{},MaterialService-findMaterialSplitScreenById:id{}", serviceResult.getTrace(), id);
        try {
            //获取彩信素材id
            Material material = materialRepository.findById(id).orElse(null);
            if (Objects.isNull(material)) {
                return serviceResult.failed().message("未找到对应彩信素材");
            }


            //根据material获取分屏列表
            Long materialId = material.getId();
            List<SplitScreen> splitScreenRepositoryList = splitScreenService.findByMaterialId(materialId)
                .stream()
                .filter(splitScreen -> Constants.UNDELETED == splitScreen.getDelFlag())
                .collect(Collectors.toList());
            //构造返回
            MaterialSplitScreenModel materialSplitScreenModel = new MaterialSplitScreenModel();
            materialSplitScreenModel.setMaterial(material);
            if (CollectionUtils.isNotEmpty(splitScreenRepositoryList)) {
                materialSplitScreenModel.setSplitScreenList(splitScreenRepositoryList);
            }
            serviceResult.succeed().data(materialSplitScreenModel);
        } catch (Exception e) {
            log.error("获取素材详情失败！", e);
            serviceResult.failed().code(HttpStatus.INTERNAL_SERVER_ERROR).message("获取素材详情失败");
            throw new RuntimeException("拖动排序失败");
        }
        return serviceResult;
    }


    /**
     * @param materialInfo 保存彩信素材
     * @return 彩信素材信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultResponse<Material> saveMaterial(Material materialInfo) {
        ResultResponse<Material> serviceResult = ResultResponse.newSuccessInstance();


        try {
            log.info("trace:{},MaterialService-saveMaterial:material{}", serviceResult.getTrace(),
                objectMapper.writeValueAsString(materialInfo));
            //落库
            long now = System.currentTimeMillis();
            buildMaterialInfo(materialInfo, now);
            materialRepository.save(materialInfo);
            serviceResult.succeed().data(materialInfo);
        } catch (Exception e) {
            log.error("保存彩信素材失败", e);
            serviceResult.failed().message("保存彩信素材失败");
            throw new RuntimeException("保存彩信素材失败");
        }
        return serviceResult;
    }


    /**
     * 构造彩信素材相信信息
     *
     * @param material 彩信素材实体
     * @param now      当前服务器时间
     */
    private void buildMaterialInfo(Material material, long now) {
        material.setCreateBy("admin");
        material.setUpdateBy("admin");
        material.setCreateName("admin");
        material.setUpdateName("admin");
        material.setUpdateDate(now);
        material.setCreateDate(now);
        material.setDelFlag(Constants.UNDELETED);
        materialRepository.save(material);
    }


    @Override
    public Material save(Material material) {
        log.debug("Request to save Material : {}", material);
        return materialRepository.save(material);
    }

    /**
     * 更新彩信素材
     *
     * @param material 彩信素材
     * @return 彩信素材对象
     * @throws JsonProcessingException JSON处理异常
     */
    @Override
    public ResultResponse<Material> update(Material material) throws JsonProcessingException {
        ResultResponse<Material> serviceResult = ResultResponse.newSuccessInstance();
        log.info("trace:{},MaterialService-update-参数:{}", serviceResult.getTrace(),
            objectMapper.writeValueAsString(material));

        try {
            if (StringUtils.isBlank(material.getMaterialName())) {
                return serviceResult.failed().message("彩信素材名称不能为空");
            }


            //校验名称是否已存在
            int materialNameExist = materialRepository.countByMaterialNameAndIdIsNot(material.getMaterialName(), material.getId());
            if (materialNameExist > Constants.ZERO) {
                return serviceResult.failed().message("名称已存在");
            }


            //落库
            material.setUpdateBy("admin");
            material.setUpdateName("admin");
            material.setUpdateDate(System.currentTimeMillis());
            materialRepository.updateMaterialInfo(material);
            serviceResult.message("修改成功").data(material);
        } catch (Exception e) {
            log.error("修改失败", e);
            serviceResult.failed().code(HttpStatus.INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
        return serviceResult;
    }


    /**
     * 彩信素材通过id删除
     *
     * @param id 彩信素材id
     * @return 删除返回
     */
    @Override
    public ResultResponse<Void> delete(Long id) {
        ResultResponse<Void> serviceResult = ResultResponse.newSuccessInstance();
        log.info("trace:{},MaterialService-delete-参数:{}", serviceResult.getTrace(), id);

        try {
            materialRepository.deleteMaterialById(id);
            splitScreenService.deleteByMaterialId(id);
        } catch (Exception e) {
            log.error("删除失败", e);
            serviceResult.failed().code(HttpStatus.INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e);
        }
        return serviceResult;
    }
}
