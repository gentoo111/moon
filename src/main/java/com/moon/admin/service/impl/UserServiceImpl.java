package com.moon.admin.service.impl;

import com.moon.admin.common.constants.UserConstants;
import com.moon.admin.common.utils.UserUtil;
import com.moon.admin.dao.UserDao;
import com.moon.admin.domain.User;
import com.moon.admin.service.UserService;
import com.moon.admin.vo.UserVO;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

/**
 * Created by szz on 2018/3/24 16:21.
 * Email szhz186@gmail.com
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

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

    @Override
    public User saveUser(UserVO userVO) {
        User user = userVO;
        user.setSalt(DigestUtils
                .md5Hex(UUID.randomUUID().toString() + System.currentTimeMillis() + UUID.randomUUID().toString()));
        user.setPassword(passwordEncoder(user.getPassword(), user.getSalt()));
        user.setStatus(User.Status.VALID);
        userDao.save(user);
        saveUserRoles(user.getId(), userVO.getRoleIds());

        log.debug("新增用户{}", user.getUsername());
        return user;
    }

    @Override
    public User updateUser(UserVO userVO) {
        userDao.update(userVO);
        saveUserRoles(userVO.getId(), userVO.getRoleIds());
        updateUserSession(userVO.getId());
        return userVO;
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User u = userDao.getUser(username);
        if (u == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        if (!u.getPassword().equals(passwordEncoder(oldPassword, u.getSalt()))) {
            throw new IllegalArgumentException("密码错误");
        }

        userDao.changePassword(u.getId(), passwordEncoder(newPassword, u.getSalt()));

        log.debug("修改{}的密码", username);
    }

    private void saveUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds != null) {
            userDao.deleteUserRole(userId);
            if (!CollectionUtils.isEmpty(roleIds)) {
                userDao.saveUserRoles(userId, roleIds);
            }
        }
    }

    private void updateUserSession(Long id) {
        User current = UserUtil.getCurrentUser();
        if (current.getId().equals(id)) {
            User user = userDao.getById(id);
            UserUtil.setUserSession(user);
        }
    }
}
