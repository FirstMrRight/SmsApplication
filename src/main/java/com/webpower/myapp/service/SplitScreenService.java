package com.webpower.myapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webpower.myapp.common.ResultResponse;
import com.webpower.myapp.domain.SplitScreen;
import com.webpower.myapp.model.MaterialSplitScreenModel;
import com.webpower.myapp.param.SplitScreenMaterialEditParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing {@link SplitScreen}.
 * @author Lt'x
 */
public interface SplitScreenService {
    /**
     * Save a splitScreen.
     *
     * @param splitScreen the entity to save.
     * @return the persisted entity.
     */
    SplitScreen save(SplitScreen splitScreen);

    /**
     * Updates a splitScreen.
     *
     * @param splitScreen the entity to update.
     * @return the persisted entity.
     */
    SplitScreen update(SplitScreen splitScreen);


    /**
     * Get all the splitScreens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SplitScreen> findAll(Pageable pageable);

    /**
     * Get the "id" splitScreen.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    ResultResponse<SplitScreen> findOne(Long id);


    /**
     * 删除分屏
     *
     * @param id         分屏Id
     * @param materialId 彩信素材id
     * @return 分屏删除信息
     */
    ResultResponse<?> delete(Long id, Long materialId);

    /**
     * 保存分屏素材
     * @param splitScreen 分屏素材
     * @param materialId 彩信素材id
     * @param materialName 彩信名称
     * @return 彩信素材与分屏信息
     * @throws JsonProcessingException JSON转换异常
     */
    ResultResponse<MaterialSplitScreenModel> saveSplitScreen(SplitScreen splitScreen, Long materialId, String materialName) throws JsonProcessingException;



    /**
     * 分屏素材拖动排序
     * @param splitScreenList 分屏列表
     * @return Service层返回体
     * @throws JsonProcessingException JSON转换异常
     */
    ResultResponse<?> dragSortScreen(List<SplitScreen> splitScreenList) throws JsonProcessingException;

    /**
     * 更新彩信分屏
     *
     * @param param 彩信分屏信息
     * @return 分屏信息修改结果
     */
    ResultResponse<?> updateSplitScreenInfo(SplitScreenMaterialEditParam param);


    /**
     * 删除彩信素材
     * @param id 彩信素材id
     */
	void deleteByMaterialId(Long id);
}
