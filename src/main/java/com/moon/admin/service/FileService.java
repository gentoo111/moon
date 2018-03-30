package com.moon.admin.service;

import com.moon.admin.domain.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by szz on 2018/3/30 11:28.
 * Email szhz186@gmail.com
 */
public interface FileService {
    FileInfo save(MultipartFile file) throws IOException;
}
