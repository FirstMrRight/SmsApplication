package com.webpower.myapp.param;

import java.math.BigDecimal;

/**
 * @author Liutx
 * @since 2023-05-17 14:26
 */
public class SplitScreenMaterialEditParam {

    /**
     * 分屏信息id
     */
    private Long SplitScreenId;

    /**
     * 彩信素材id
     */
    private Long materialId;

    /**
     * 彩信素材名称
     */
    private String materialName;

    /**
     * 内容
     */
    private String content;

    /**
     * 分屏图片url
     */
    private String pictureUrl;

    /**
     * 分屏图片大小(kb)
     */
    private BigDecimal pictureSize;

    public Long getSplitScreenId() {
        return SplitScreenId;
    }

    public void setSplitScreenId(Long splitScreenId) {
        SplitScreenId = splitScreenId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public BigDecimal getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(BigDecimal pictureSize) {
        this.pictureSize = pictureSize;
    }
}
