package com.moon.admin.service.impl;

import com.moon.admin.common.utils.FileUtils;
import com.moon.admin.dao.FileInfoDao;
import com.moon.admin.domain.FileInfo;
import com.moon.admin.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by szz on 2018/3/30 11:28.
 * Email szhz186@gmail.com
 */
@Service
public class FileServiceImpl implements FileService {


    private static final Logger LOGGER = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private FileInfoDao fileInfoDao;

    @Value("${files.path}")
    private String filesPath;

    @Override
    public FileInfo save(MultipartFile file) throws IOException{
        String fileOrigName = file.getOriginalFilename();
        if (!fileOrigName.contains(".")) {
            throw new IllegalArgumentException("缺少后缀名");
        }

        String md5 = FileUtils.fileMd5(file.getInputStream());
        FileInfo fileInfo = fileInfoDao.getById(md5);
        if (fileInfo != null) {
            fileInfoDao.update(fileInfo);
            return fileInfo;
        }

        fileOrigName = fileOrigName.substring(fileOrigName.lastIndexOf("."));

        //String path2 = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        //获取根目录

        File path = new File(ResourceUtils.getURL("classpath:").getPath());
//        if (!path.exists()) {
//            path = new File("");
//        }
        //如果上传目录为/static/images/upload/，则可以如下获取：
        File upload = new File(path.getAbsolutePath(), "static/images/upload/");
        if (!upload.exists()){
            upload.mkdirs();
        }
        //在开发测试模式时，得到的地址为：{项目根目录}/target/static/images/upload/
        //在打包成jar正式发布时，得到的地址为：{发布jar包目录}/static/images/upload/
        String absolutePath = upload.getAbsolutePath();

        String pathname = FileUtils.getPath() + md5 + fileOrigName;
        String fullPath = absolutePath + pathname;
        FileUtils.saveFile(file, fullPath);

        long size = file.getSize();
        String contentType = file.getContentType();

        fileInfo = new FileInfo();
        fileInfo.setId(md5);
        fileInfo.setContentType(contentType);
        fileInfo.setSize(size);
        fileInfo.setPath(fullPath);
        fileInfo.setUrl(pathname);
        fileInfo.setType(contentType.startsWith("image/") ? 1 : 0);

        fileInfoDao.save(fileInfo);

        LOGGER.debug("上传文件{}", fullPath);

        return fileInfo;
    }


    @Override
    public void delete(String id) {
        FileInfo fileInfo = fileInfoDao.getById(id);
        if (fileInfo != null) {
            String fullPath = fileInfo.getPath();
            FileUtils.deleteFile(fullPath);

            fileInfoDao.delete(id);
            LOGGER.debug("删除文件：{}", fileInfo.getPath());
        }
    }

    @Override
    public int count(Map<String, Object> params) {
        return fileInfoDao.count(params);
    }

    @Override
    public List<FileInfo> list(Map<String, Object> params, Integer offset, Integer limit) {
        return fileInfoDao.list(params,offset,limit);
    }
}
