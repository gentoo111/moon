package com.moon.admin.controller;

import com.moon.admin.common.utils.UserUtil;
import com.moon.admin.domain.User;
import com.moon.admin.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by szz on 2018/3/24 16:17.
 * Email szhz186@gmail.com
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER= LoggerFactory.getLogger("adminLogger");

    @Autowired
    private UserService userService;

    @ApiOperation(value = "当前登录用户")
    @GetMapping("/current")
    public User currentUser(){
        return UserUtil.getCurrentUser();
    }

    @ApiOperation(value = "根据用户id获取用户")
    @GetMapping("/{id}")
    @RequiresPermissions("sys:user:query")
    public User getById(@PathVariable Long id){
        return userService.getById(id);
    }
}
