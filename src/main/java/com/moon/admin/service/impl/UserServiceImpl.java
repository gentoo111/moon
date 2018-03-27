package com.moon.admin.service.impl;

import com.moon.admin.common.constants.UserConstants;
import com.moon.admin.dao.UserDao;
import com.moon.admin.domain.User;
import com.moon.admin.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by szz on 2018/3/24 16:21.
 * Email szhz186@gmail.com
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String username) {
        return userDao.getUser(username);
    }

    @Override
    public String passwordEncoder(String credentials, String salt) {
        SimpleHash simpleHash = new SimpleHash("MD5", credentials, salt, UserConstants.HASH_ITERATIONS);
        return simpleHash.toString();
    }

    @Override
    public User getById(Long id) {
        return userDao.getById(id);
    }
}
