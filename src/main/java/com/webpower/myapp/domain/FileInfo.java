package com.webpower.myapp.domain;

import java.math.BigDecimal;

/**
 * @author Liutx
 * @since 2023-05-17 16:26
 */
public class FileInfo {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件大小
     */
    private BigDecimal fileSize;

    /**
     * 文件访问路径
     */
    private String url;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public BigDecimal getFileSize() {
        return fileSize;
    }

    public void setFileSize(BigDecimal fileSize) {
        this.fileSize = fileSize;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
