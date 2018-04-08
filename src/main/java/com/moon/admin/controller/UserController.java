package com.moon.admin.controller;

import com.moon.admin.common.utils.LogAnnotation;
import com.moon.admin.common.utils.UserUtil;
import com.moon.admin.dao.UserDao;
import com.moon.admin.domain.User;
import com.moon.admin.service.UserService;
import com.moon.admin.vo.UserVO;
import com.moon.admin.vo.page.PageTableHandler;
import com.moon.admin.vo.page.PageTableRequest;
import com.moon.admin.vo.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private UserDao userDao;

    @LogAnnotation
    @PostMapping
    @ApiOperation(value = "保存用户")
    @RequiresPermissions("sys:user:add")
    public User saveUser(@RequestBody UserVO userVO) {
        User u = userService.getUser(userVO.getUsername());
        if (u != null) {
            throw new IllegalArgumentException(userVO.getUsername() + "已存在");
        }

        return userService.saveUser(userVO);
    }

    @LogAnnotation
    @PutMapping
    @ApiOperation(value = "修改用户")
    @RequiresPermissions("sys:user:add")
    public User updateUser(@RequestBody UserVO userVO) {
        return userService.updateUser(userVO);
    }

    @LogAnnotation
    @PutMapping(params = "headImgUrl")
    @ApiOperation(value = "修改头像")
    public void updateHeadImgUrl(String headImgUrl) {
        User user = UserUtil.getCurrentUser();
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setHeadImgUrl(headImgUrl);

        userService.updateUser(userVO);
        LOGGER.debug("{}修改了头像", user.getUsername());
    }

    @LogAnnotation
    @PutMapping("/{username}")
    @ApiOperation(value = "修改密码")
    @RequiresPermissions("sys:user:password")
    public void changePassword(@PathVariable String username, String oldPassword, String newPassword) {
        userService.changePassword(username, oldPassword, newPassword);
    }

    @GetMapping
    @ApiOperation(value = "用户列表")
    @RequiresPermissions("sys:user:query")
    public PageTableResponse listUsers(PageTableRequest request) {
        return new PageTableHandler(new PageTableHandler.CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return userDao.count(request.getParams());
            }
        }, new PageTableHandler.ListHandler() {

            @Override
            public List<User> list(PageTableRequest request) {
                List<User> list = userDao.list(request.getParams(), request.getOffset(), request.getLimit());
                return list;
            }
        }).handle(request);
    }
}
