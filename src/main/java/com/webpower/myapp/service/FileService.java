package com.webpower.myapp.service;

import com.webpower.myapp.common.ResultResponse;
import com.webpower.myapp.domain.FileInfo;
import com.webpower.myapp.domain.Material;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Liutx
 * @since 2023-05-17 15:53
 */
public interface FileService {


    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件访问路径
     */
    ResultResponse<FileInfo> fileUpload(MultipartFile file);
}
