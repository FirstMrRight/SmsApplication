package com.webpower.myapp.web.rest;

import com.webpower.myapp.common.ResultResponse;
import com.webpower.myapp.domain.FileInfo;
import com.webpower.myapp.service.impl.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Liutx
 * @since 2023-05-17 15:52
 */
@RestController
@RequestMapping("file")
public class FileResource {

    private final Logger log = LoggerFactory.getLogger(FileResource.class);

    private final FileServiceImpl fileService;


    @Autowired
    public FileResource(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    /**
     * 上传文件
     *
     * @param file 待上传的文件
     * @return 文件url
     */
    @PostMapping("upload")
    public ResultResponse<FileInfo> fileUpload(@RequestParam("file") MultipartFile file) {

        log.info("File:上传文件开始,文件名称：{},大小:{}", file.getOriginalFilename(), file.getSize());
        return fileService.fileUpload(file);
    }

}
