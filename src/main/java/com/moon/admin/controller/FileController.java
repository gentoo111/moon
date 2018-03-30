package com.moon.admin.controller;

import com.moon.admin.common.utils.LogAnnotation;
import com.moon.admin.domain.FileInfo;
import com.moon.admin.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by szz on 2018/3/30 11:25.
 * Email szhz186@gmail.com
 */
@Api(tags = "文件")
@RestController
@RequestMapping("/files")
public class FileController {


    @Autowired
    private FileService fileService;

    @LogAnnotation
    @PostMapping
    @ApiOperation(value = "文件上传")
    public FileInfo uploadFile(MultipartFile file) throws IOException{
        return fileService.save(file);
    }
}
