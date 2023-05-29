package com.webpower.myapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webpower.myapp.common.ResultResponse;
import com.webpower.myapp.domain.Material;
import com.webpower.myapp.model.MaterialSplitScreenModel;
import com.webpower.myapp.service.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * REST controller for managing {@link com.webpower.myapp.domain.Material}.
 * @author Lt'x
 */
@RestController
@RequestMapping("/material")
public class MaterialResource {

    private final Logger log = LoggerFactory.getLogger(MaterialResource.class);

    private final ObjectMapper objectMapper = new ObjectMapper();


    private final MaterialService materialService;

    public MaterialResource(MaterialService materialService) {
        this.materialService = materialService;
    }

    /**
     * 创建彩信素材
     * @param material 彩信素材
     * @return 彩信素材和分屏列表
     * @throws JsonProcessingException JSON处理异常
     */
    @PostMapping()
    public ResultResponse<Material> createMaterial(@RequestBody Material material) throws JsonProcessingException {
        log.info("REST request to save Material : {}", material);
        return materialService.saveMaterial(material);
    }


    /**
     * 更新彩信素材
     *
     * @param id       彩信素材id
     * @param material 彩信素材实体
     * @return 彩信素材
     * @throws JsonProcessingException JSON处理异常
     */
    @PutMapping("/{id}")
    public ResultResponse<Material> updateMaterial(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Material material) throws JsonProcessingException {
        log.info("Material:编辑彩信素材开始,参数：{}，id:{}", objectMapper.writeValueAsString(material), id);

        if (Objects.isNull(id)) {
            return new ResultResponse<Material>().message("请选择彩信素材");
        }
        material.setId(id);
        return materialService.update(material);
    }


    /**
     * 获取彩信素材列表
     *
     * @param keyword 搜索关键字
     * @param page    页数
     * @param size    每页数量
     * @return 获取彩信素材列表
     * @throws JsonProcessingException Json转换异常
     */
    @GetMapping
    public ResultResponse<Page<Material>> getAllMaterials(@RequestParam(required = false) String keyword,
                                                          @RequestParam("page") int page,
                                                          @RequestParam("size") int size) throws JsonProcessingException {
        //组装分页信息
        Sort sortInfo = Sort.by(Sort.Direction.DESC, "createDate");
        Pageable pageable = PageRequest.of(page - 1, size, sortInfo);


        log.info("Material:获取彩信素材列表开始,参数：{}", objectMapper.writeValueAsString(pageable));
        return materialService.findMaterialListByNameOrCreateBy(pageable, keyword);
    }


    /**
     * 根据id获取彩信素材和分屏预览
     *
     * @param id 彩信
     * @return 彩信素材和分屏
     */
    @GetMapping("/{id}")
    public ResultResponse<MaterialSplitScreenModel> getMaterial(@PathVariable Long id) {
        log.info("Material:获取彩信素材开始，id: {}", id);
        if (Objects.isNull(id)) {
            return new ResultResponse<MaterialSplitScreenModel>().message("请选择彩信素材");
        }
        return materialService.findMaterialSplitScreenById(id);
    }


    /**
     * 删除彩信素材
     *
     * @param id 彩信素材id
     * @return 彩信素材删除结果
     */
    @DeleteMapping("/{id}")
    public ResultResponse<?> deleteMaterial(@PathVariable Long id) {
        log.debug("Material:删除彩信素材开始，id: {}", id);

        if (Objects.isNull(id)) {
            return new ResultResponse<>().failed().code(HttpStatus.BAD_REQUEST).message("请选择彩信素材");
        }
        return materialService.delete(id);
    }
}
