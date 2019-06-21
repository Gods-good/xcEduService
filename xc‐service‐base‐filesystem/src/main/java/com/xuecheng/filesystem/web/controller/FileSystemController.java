package com.xuecheng.filesystem.web.controller;

import com.xuecheng.api.files.FileSystemControllerApi;
import com.xuecheng.filesystem.service.FileSystemService;
import com.xuecheng.framework.domain.filesystem.response.UploadFileResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description
 * @auther Jack
 * @create 2019-06-11 14:52
 */
@RestController
public class FileSystemController implements FileSystemControllerApi {
    @Autowired
    FileSystemService fileSystemService;
    @Override
    public UploadFileResult upload(MultipartFile file, String businesskey, String filetag, String metadata) {
        return fileSystemService.upload(file,businesskey,filetag,metadata);
    }
}
