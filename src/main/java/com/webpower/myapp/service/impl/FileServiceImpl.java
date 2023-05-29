package com.webpower.myapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webpower.myapp.common.ResultResponse;
import com.webpower.myapp.domain.FileInfo;
import com.webpower.myapp.service.FileService;
import com.webpower.myapp.web.rest.MaterialResource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * @author Liutx
 * @since 2023-05-17 15:54
 */
@Service
public class FileServiceImpl implements FileService {


    private final Logger log = LoggerFactory.getLogger(MaterialResource.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${upload.service}")
    private String service;

    @Value("${upload.uri}")
    private String uri;

    @Value("${upload.size}")
    private long fileSize;

    @Value("${local.upload.path}")
    private String path;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件访问路径
     */
    @Override
    public ResultResponse<FileInfo> fileUpload(MultipartFile file) {
        ResultResponse<FileInfo> serviceResult = ResultResponse.newSuccessInstance();
        log.info("trace:{}", serviceResult.getTrace());

        try {
            //文件校验
            if (fileSize < file.getSize()) {
                long realFileSize = fileSize * 1024 * 1024;
                serviceResult.failed().code(HttpStatus.BAD_REQUEST).message("上传文件请小于" + realFileSize);
            }

            boolean uploadServiceFlag = StringUtils.isBlank(service) || StringUtils.isBlank(uri);
            if (uploadServiceFlag) {
                return serviceResult.code(HttpStatus.INTERNAL_SERVER_ERROR).message("无法上传，请联系管理员");
            }


            String fileName = org.springframework.util.StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String filePath = path + fileName;
            // 创建目标文件
            Path destPath = new File(filePath).toPath();
            // 将上传的文件保存到目标文件
            Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("文件服务返回信息为:{}", destPath);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileName(file.getOriginalFilename());
            fileInfo.setFileSize(BigDecimal.valueOf(file.getSize()));
            log.info(objectMapper.writeValueAsString(destPath));
            String convertedPath = destPath.toString().replace("\\", "/");
            fileInfo.setUrl(convertedPath);
            serviceResult.code(HttpStatus.OK).message("上传成功").data(fileInfo);
        } catch (Exception e) {
            log.info("文件上传失败", e);
            serviceResult.code(HttpStatus.INTERNAL_SERVER_ERROR).message("文件上传失败");
            throw new RuntimeException(e);
        }
        return serviceResult;
    }

}
