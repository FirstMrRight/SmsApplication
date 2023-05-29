package com.webpower.myapp.model;

import com.webpower.myapp.domain.Material;
import com.webpower.myapp.domain.SplitScreen;

import java.io.Serializable;
import java.util.List;

/**
 * @author Liutx
 * @since 2023-05-16 14:29
 */
public class MaterialSplitScreenModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 彩信素材
     */
    private Material material;

    /**
     * 分屏列表
     */
    private List<SplitScreen> splitScreenList;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<SplitScreen> getSplitScreenList() {
        return splitScreenList;
    }

    public void setSplitScreenList(List<SplitScreen> splitScreenList) {
        this.splitScreenList = splitScreenList;
    }
}
