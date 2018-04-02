package com.moon.admin.service;

import com.moon.admin.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by szz on 2018/3/30 11:28.
 * Email szhz186@gmail.com
 */
public interface FileService {
    FileInfo save(MultipartFile file) throws IOException;

    void delete(String id);

    int count(Map<String, Object> params);

    List<FileInfo> list(Map<String, Object> params, Integer offset, Integer limit);
}
