package com.webpower.myapp.model;

import com.webpower.myapp.domain.Material;
import com.webpower.myapp.domain.SplitScreen;

/**
 * @author Liutx
 * @since 2023-05-16 18:11
 */
public class MaterialSpiltScreen {

    /**
     * 彩信素材
     */
    private Material material;

    /**
     * 分屏素材
     */
    private SplitScreen splitScreen;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public SplitScreen getSplitScreen() {
        return splitScreen;
    }

    public void setSplitScreen(SplitScreen splitScreen) {
        this.splitScreen = splitScreen;
    }
}
