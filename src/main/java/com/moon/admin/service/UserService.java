package com.moon.admin.service;

import com.moon.admin.domain.User;

/**
 * Created by szz on 2018/3/24 16:19.
 * Email szhz186@gmail.com
 */
public interface UserService {
    User getUser(String username);

    String passwordEncoder(String credentials, String salt);

    User getById(Long id);
}
