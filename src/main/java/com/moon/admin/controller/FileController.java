package com.moon.admin.controller;

import com.moon.admin.common.utils.LogAnnotation;
import com.moon.admin.dao.FileInfoDao;
import com.moon.admin.domain.FileInfo;
import com.moon.admin.service.FileService;
import com.moon.admin.vo.LayuiFile;
import com.moon.admin.vo.page.PageTableHandler.*;
import com.moon.admin.vo.page.PageTableHandler;
import com.moon.admin.vo.page.PageTableRequest;
import com.moon.admin.vo.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
    public FileInfo uploadFile(MultipartFile file) throws IOException {
        return fileService.save(file);
    }

    /**
     * layui富文本文件自定义上传
     *
     * @param file
     * @param domain
     * @return
     * @throws IOException
     */
    @LogAnnotation
    @PostMapping("/layui")
    @ApiOperation(value = "layui富文本文件自定义上传")
    public LayuiFile uploadLayuiFile(MultipartFile file, String domain) throws IOException {
        FileInfo fileInfo = fileService.save(file);

        LayuiFile layuiFile = new LayuiFile();
        layuiFile.setCode(0);
        LayuiFile.LayuiFileData data = new LayuiFile.LayuiFileData();
        layuiFile.setData(data);
        data.setSrc(domain + "/files" + fileInfo.getUrl());
        data.setTitle(file.getOriginalFilename());

        return layuiFile;
    }

    @GetMapping
    @ApiOperation(value = "文件查询")
    @RequiresPermissions("sys:file:query")
    public PageTableResponse listFiles(PageTableRequest request) {
        System.out.println(request);
        return new PageTableHandler(new CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return fileService.count(request.getParams());
            }
        }, new ListHandler() {

            @Override
            public List<FileInfo> list(PageTableRequest request) {
                List<FileInfo> list = fileService.list(request.getParams(), request.getOffset(), request.getLimit());
                return list;
            }
        }).handle(request);
    }

    @LogAnnotation
    @DeleteMapping("/{id}")
    @ApiOperation(value = "文件删除")
    @RequiresPermissions("sys:file:del")
    public void delete(@PathVariable String id) {
        fileService.delete(id);
    }
}
