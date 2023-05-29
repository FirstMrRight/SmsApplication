package com.webpower.myapp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webpower.myapp.common.ResultResponse;
import com.webpower.myapp.config.Constants;
import com.webpower.myapp.domain.Material;
import com.webpower.myapp.domain.SplitScreen;
import com.webpower.myapp.model.MaterialSplitScreenModel;
import com.webpower.myapp.param.SplitScreenMaterialEditParam;
import com.webpower.myapp.repository.MaterialRepository;
import com.webpower.myapp.repository.SplitScreenRepository;
import com.webpower.myapp.service.SplitScreenService;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service Implementation for managing {@link SplitScreen}.
 *
 * @author Lt'x
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SplitScreenServiceImpl implements SplitScreenService {

    private final Logger log = LoggerFactory.getLogger(SplitScreenServiceImpl.class);

    private static final BigDecimal MAX_SPLIT_SCREEN_SIZE = BigDecimal.valueOf(80.00);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SplitScreenRepository splitScreenRepository;

    private final MaterialRepository materialRepository;

    public SplitScreenServiceImpl(SplitScreenRepository splitScreenRepository,
                                  MaterialRepository materialRepository) {
        this.splitScreenRepository = splitScreenRepository;
        this.materialRepository = materialRepository;
    }

    @Override
    public SplitScreen save(SplitScreen splitScreen) {
        log.debug("Request to save SplitScreen : {}", splitScreen);
        return splitScreenRepository.save(splitScreen);
    }

    @Override
    public SplitScreen update(SplitScreen splitScreen) {
        log.debug("Request to update SplitScreen : {}", splitScreen);
        return splitScreenRepository.save(splitScreen);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<SplitScreen> findAll(Pageable pageable) {
        log.debug("Request to get all SplitScreens");
        return splitScreenRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ResultResponse<SplitScreen> findOne(Long id) {
        ResultResponse<SplitScreen> serviceResult = ResultResponse.newSuccessInstance();
        log.info("trace:{},SplitScreenService findOne : {}", serviceResult.getTrace(), id);

        try {
            SplitScreen splitScreen = splitScreenRepository.findById(id).orElse(null);
            if (Objects.isNull(splitScreen)) {
                return serviceResult.failed().code(HttpStatus.NOT_FOUND).message("该分屏信息已删除");
            }
            serviceResult.succeed().message("查询成功").data(splitScreen);
        } catch (Exception e) {
            log.error("获取分屏预览失败", e);
            serviceResult.failed().code(HttpStatus.INTERNAL_SERVER_ERROR).message("获取分屏预览失败");
            throw new RuntimeException(serviceResult.getTrace() + "获取分屏预览失败");
        }
        return serviceResult;
    }


    /**
     * 删除分屏
     *
     * @param id         分屏Id
     * @param materialId 彩信素材id
     * @return 分屏删除信息
     */
    @Override
    public ResultResponse<?> delete(Long id, Long materialId) {
        ResultResponse<?> serviceResult = ResultResponse.newSuccessInstance();
        log.info("trace:{},SplitScreenService findOne : {}", serviceResult.getTrace(), id);

        try {
            //校验当前materialId下有几个分屏
            Integer count = splitScreenRepository.countByMaterialId(materialId);
            if (count <= Constants.ONE) {
                return serviceResult.failed().code(HttpStatus.BAD_REQUEST).message("一个彩信素材至少有一个分屏");
            }
            splitScreenRepository.deleteSplitScreenById(id);
        } catch (Exception e) {
            log.error("删除分屏失败", e);
            serviceResult.failed().code(HttpStatus.INTERNAL_SERVER_ERROR).message("删除分屏失败");
            throw new RuntimeException(serviceResult.getTrace() + "删除分屏预览失败");
        }

        return serviceResult;
    }

    /**
     * 保存分屏素材
     *
     * @param splitScreen  分屏素材
     * @param materialId   彩信素材id
     * @param materialName 彩信名称
     * @return 分屏信息与彩信信息
     */
    @Override
    public ResultResponse<MaterialSplitScreenModel> saveSplitScreen(SplitScreen splitScreen, Long materialId, String materialName) throws JsonProcessingException {
        ResultResponse<MaterialSplitScreenModel> serviceResult = ResultResponse.newSuccessInstance();
        log.info("trace:{},SplitScreenService-saveSplitScreen:splitScreen{},materialId:{},materialName:{}",
            serviceResult.getTrace(),
            objectMapper.writeValueAsString(splitScreen),
            materialId,
            materialName);

        try {
            //校验短信素材名称是否存在
            Material materialModel = materialRepository.findByMaterialName(materialName);
            if (ObjectUtils.isNotEmpty(materialModel)) {
                return serviceResult.failed().code(HttpStatus.BAD_REQUEST).message("彩信素材名称重复");
            }

            //保存彩信素材
            Material material = new Material();
            material.setId(materialId);
            material.setMaterialName(materialName);
            material.setType(Constants.SMS_TYPE);
            long now = System.currentTimeMillis();
            material.setUpdateDate(now);
            material.setCreateDate(now);
            material.setCreateBy("admin");
            material.setUpdateBy("admin");
            material.setUpdateName("admin");
            material.setCreateName("admin");
            material.setDelFlag(Constants.UNDELETED);
            log.info("Material落库信息:{}", objectMapper.writeValueAsString(material));
            materialRepository.save(material);

            //处理分屏素材，校验已存在的分屏和现在添加的分屏图片是否大于80KB
            boolean splitScreenPictureSizeFlag = checkSplitScreenPictureSize(splitScreen, materialId);
            if (splitScreenPictureSizeFlag) {
                return serviceResult.failed().code(HttpStatus.INTERNAL_SERVER_ERROR).message("上传的分屏图片大于80k");
            }

            //落库，同时查询当前material下最新的排序
            buildSpiltScreen(splitScreen, materialId, now);
            log.info("SplitScreen落库信息:{}", objectMapper.writeValueAsString(splitScreen));
            splitScreenRepository.insertSpiltScreen(splitScreen);
        } catch (Exception e) {
            log.error("创建分屏信息失败", e);
            serviceResult.failed().code(HttpStatus.INTERNAL_SERVER_ERROR).message("创建分屏信息失败");
            throw new RuntimeException("创建分屏信息失败");
        }
        return serviceResult;
    }


    /**
     * 拖动排序
     *
     * @param splitScreenList 分屏列表
     * @return Service层返回体
     */
    @Override
    public ResultResponse<?> dragSortScreen(List<SplitScreen> splitScreenList) throws JsonProcessingException {
        ResultResponse<MaterialSplitScreenModel> serviceResult = ResultResponse.newSuccessInstance();
        log.info("trace:{},SplitScreenService-dragScreen:splitScreenList{}", serviceResult.getTrace(), objectMapper.writeValueAsString(serviceResult));

        try {
            for (SplitScreen splitScreen : splitScreenList) {
                splitScreenRepository.dragScreen(splitScreen);
            }
            serviceResult.message("拖动排序操作成功");
        } catch (Exception e) {
            log.error("拖动排序失败", e);
            serviceResult.failed().message("拖动排序失败");
            throw new RuntimeException("拖动排序失败");
        }
        return serviceResult;
    }


    /**
     * 更新彩信分屏
     *
     * @param param 彩信分屏信息
     * @return 分屏信息修改结果
     */
    @Override
    public ResultResponse<?> updateSplitScreenInfo(SplitScreenMaterialEditParam param) {
        ResultResponse<?> serviceResult = ResultResponse.newSuccessInstance();
        log.info("traceId:{}", serviceResult.getTrace());

        try {
            if (!splitScreenRepository.existsById(param.getSplitScreenId())) {
                return ResultResponse.newFailedInstance().message("分屏信息不存在");
            }

            int materialNameExist = materialRepository.countByMaterialNameAndIdIsNot(param.getMaterialName(), param.getMaterialId());
            if (materialNameExist > Constants.ZERO) {
                return serviceResult.failed().message("名称已存在");
            }


            SplitScreen splitScreen = new SplitScreen();
            BeanUtils.copyProperties(param, splitScreen);
            splitScreen.setId(param.getSplitScreenId());
            //校验素材图片大小是否超过80K
            boolean splitScreenPictureSizeFlag = checkSplitScreenPictureSize(splitScreen, param.getMaterialId());
            if (splitScreenPictureSizeFlag) {
                return serviceResult.failed().code(HttpStatus.BAD_REQUEST).message("分屏图片大于80K");
            }


            //构造落库信息
            Material material = new Material();
            material.setId(param.getMaterialId());
            material.setMaterialName(param.getMaterialName());
            long now = System.currentTimeMillis();
            material.setUpdateDate(now);
            material.setUpdateBy("admin");
            material.setUpdateName("admin");
            materialRepository.updateMaterialInfo(material);
            splitScreen.setUpdateName("admin");
            splitScreen.setUpdateBy("admin");
            splitScreen.setUpdateDate(now);
            splitScreenRepository.updateSplitScreen(splitScreen);
        } catch (Exception e) {
            log.error("修改分屏信息失败", e);
            serviceResult.failed().message("修改分屏信息失败");
            throw new RuntimeException(e);
        }
        return serviceResult;
    }

    /**
     * 通过彩信素材id删除分屏
     *
     * @param id 彩信素材id
     */
    @Override
    public void deleteByMaterialId(Long id) {
        ResultResponse<?> serviceResult = ResultResponse.newSuccessInstance();
        log.info("traceId:{}", serviceResult.getTrace());

        try {
            splitScreenRepository.deleteSplitScreenByMaterialId(id);
        } catch (Exception e) {
            log.error("通过彩信素材id删除分屏失败", e);
            serviceResult.failed().message("通过彩信素材id删除分屏失败");
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造基础分屏对象
     *
     * @param splitScreen 分屏对象
     * @param materialId  分屏对象所属彩信素材id
     * @param now         当前服务器时间
     */
    private void buildSpiltScreen(SplitScreen splitScreen, Long materialId, long now) {
        splitScreen.setMaterialId(materialId);
        splitScreen.setCreateBy("admin");
        splitScreen.setUpdateBy("admin");
        splitScreen.setCreateName("admin");
        splitScreen.setUpdateName("admin");
        splitScreen.setCreateDate(now);
        splitScreen.setUpdateDate(now);
        splitScreen.setDelFlag(Constants.UNDELETED);
    }

    public List<SplitScreen> findByMaterialId(Long materialId) {
        return splitScreenRepository.findByMaterialId(materialId);
    }


    /**
     * 检验彩信分屏图片总大小是否超过80KB
     *
     * @param splitScreen 彩信分屏
     * @param materialId  彩信id
     * @return 是否大于80K
     */
    private boolean checkSplitScreenPictureSize(SplitScreen splitScreen, Long materialId) {
        List<SplitScreen> splitScreenList = new ArrayList<>();
        List<SplitScreen> existSpiltScreen = splitScreenRepository.findByMaterialId(materialId);
        splitScreenList.add(splitScreen);
        splitScreenList.addAll(existSpiltScreen);
        BigDecimal splitScreenAllPictureSizeSum = splitScreenList.stream().map(SplitScreen::getPictureSize)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        int compareFlag = MAX_SPLIT_SCREEN_SIZE.compareTo(splitScreenAllPictureSizeSum);
        return Constants.UNDELETED >= compareFlag;
    }
}
