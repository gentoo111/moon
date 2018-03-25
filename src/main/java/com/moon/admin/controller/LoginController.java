package com.moon.admin.controller;

import com.zw.admin.server.annotation.LogAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by szz on 2018/3/25 21:04.
 * Email szhz186@gmail.com
 */
@Api(tags = "登陆")
@RestController
@RequestMapping
public class LoginController {

    @LogAnnotation
    @ApiOperation(value = "web端登陆")
    @PostMapping("/sys/login")
    public void login(String username,String password){
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        SecurityUtils.getSubject().login(usernamePasswordToken);
    }

}
