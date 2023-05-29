package com.webpower.myapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webpower.myapp.common.ResultResponse;
import com.webpower.myapp.domain.Material;
import com.webpower.myapp.model.MaterialSplitScreenModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Material}.
 * @author Lt'x
 */
public interface MaterialService {
    /**
     * Save a material.
     *
     * @param material the entity to save.
     * @return the persisted entity.
     */
    Material save(Material material);


    /**
     * 更新彩信素材信息
     * @param material 彩信素材
     * @return 更新后的彩信素材
     * @throws JsonProcessingException JSON转换异常
     */
    ResultResponse<Material> update(Material material) throws JsonProcessingException;


    /**
     * 删除彩信素材
     *
     * @param id 彩信素材id
     * @return 彩信素材删除结果
     */
    ResultResponse<Void> delete(Long id);

    /**
     * 获取素材分页列表
     * @param pageable 分页信息
     * @param keyword 搜索关键词
     * @return 素材分页列表
     */
    ResultResponse<Page<Material>> findMaterialListByNameOrCreateBy(Pageable pageable, String keyword);

    /**
     * 根据彩信素材id获取彩信素材及分屏预览
     *
     * @param id 彩信素材
     * @return 彩信素材id获取彩信素材及分屏预览
     */
    ResultResponse<MaterialSplitScreenModel> findMaterialSplitScreenById(Long id);

    /**
     * 保存彩信素材
     * @param material 彩信素材实体
     * @return 彩信素材
     * @throws JsonProcessingException JSON转换失败
     */
    ResultResponse<Material> saveMaterial(Material material) throws JsonProcessingException;

}
