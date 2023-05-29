package com.webpower.myapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webpower.myapp.common.ResultResponse;
import com.webpower.myapp.domain.SplitScreen;
import com.webpower.myapp.model.MaterialSplitScreenModel;
import com.webpower.myapp.param.SplitScreenMaterialEditParam;
import com.webpower.myapp.service.SplitScreenService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;
import java.util.Objects;

/**
 * REST controller for managing {@link com.webpower.myapp.domain.SplitScreen}.
 * @author Lt'x
 */
@RestController
@RequestMapping("/split_screen")
public class SplitScreenResource {

    private final Logger log = LoggerFactory.getLogger(SplitScreenResource.class);


    private final ObjectMapper objectMapper = new ObjectMapper();


    private final SplitScreenService splitScreenService;


    public SplitScreenResource(SplitScreenService splitScreenService) {
        this.splitScreenService = splitScreenService;
    }

    /**
     * 新增分屏信息
     *
     * @param splitScreen  分屏信息
     * @param materialId   所属主素材id
     * @param materialName 主素材名称
     * @return 子素材信息
     * @throws JsonProcessingException JSON处理异常
     */
    @PostMapping
    public ResultResponse<MaterialSplitScreenModel> createSplitScreen(@RequestBody SplitScreen splitScreen,
                                                                      @RequestParam Long materialId,
                                                                      @RequestParam String materialName) throws JsonProcessingException {
        log.info("SplitScreen:新增彩信分屏素材开始,参数：{},materialId:{},materialName:{}", objectMapper.writeValueAsString(splitScreen),
            objectMapper.writeValueAsString(materialId),
            objectMapper.writeValueAsString(materialName));

        return splitScreenService.saveSplitScreen(splitScreen, materialId, materialName);
    }

    /**
     * 更新彩信分屏
     *
     * @param id    彩信分屏id
     * @param param 彩信分屏信息
     * @return 分屏信息修改结果
     * @throws JsonProcessingException JSON转换异常
     */
    @PutMapping("/split-screens/{id}")
    public ResultResponse<?> updateSplitScreen(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SplitScreenMaterialEditParam param) throws JsonProcessingException {
        log.info("SplitScreen:编辑彩信分屏素材开始,参数：{},SplitScreenId:{}",
            objectMapper.writeValueAsString(param), id);


        if (Objects.isNull(id)) {
            return ResultResponse.newFailedInstance().message("请选择分屏信息");
        }
        param.setSplitScreenId(id);
        return splitScreenService.updateSplitScreenInfo(param);
    }

    @PatchMapping("/sort")
    public ResultResponse<?> dragSort(@RequestBody List<SplitScreen> splitScreenList) throws JsonProcessingException {
        log.info("SplitScreen:彩信分屏素材排序开始,参数：{}", objectMapper.writeValueAsString(splitScreenList));
        if (CollectionUtils.isEmpty(splitScreenList)) {
            return ResultResponse.newFailedInstance().code(HttpStatus.BAD_REQUEST).message("请选择分屏信息");
        }

        return splitScreenService.dragSortScreen(splitScreenList);
    }


    /**
     * {@code GET  /split-screens} : get all the splitScreens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of splitScreens in body.
     */
    @GetMapping("/split-screens")
    public ResponseEntity<List<SplitScreen>> getAllSplitScreens(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SplitScreens");
        Page<SplitScreen> page = splitScreenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * 获取分屏素材详情-预览
     *
     * @param id 分屏素材Id
     * @return 分屏素材详情
     */
    @GetMapping("/{id}")
    public ResultResponse<SplitScreen> getSplitScreenInfo(@PathVariable Long id) {
        log.debug("SplitScreen: 获取分屏素材详情: {}", id);
        return splitScreenService.findOne(id);
    }

    /**
     * 删除分屏
     *
     * @param id 分屏Id
     * @return 分屏删除信息
     */
    @DeleteMapping("/split-screens/{id}")
    public ResultResponse<?> deleteSplitScreen(@PathVariable Long id,
                                               @RequestParam Long materialId) {
        log.debug("SplitScreen: 删除分屏素材: {},materialId:{}", id, materialId);

        return splitScreenService.delete(id, materialId);
    }
}
