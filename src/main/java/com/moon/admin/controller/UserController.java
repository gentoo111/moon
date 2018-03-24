package com.moon.admin.controller;

import com.moon.admin.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;
}
