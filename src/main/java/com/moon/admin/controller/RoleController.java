package com.moon.admin.controller;

import com.google.common.collect.Maps;
import com.moon.admin.common.utils.LogAnnotation;
import com.moon.admin.dao.RoleDao;
import com.moon.admin.domain.Role;
import com.moon.admin.service.RoleService;
import com.moon.admin.vo.RoleVO;
import com.moon.admin.vo.page.PageTableHandler;
import com.moon.admin.vo.page.PageTableRequest;
import com.moon.admin.vo.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by szz on 2018/3/27 11:57.
 * Email szhz186@gmail.com
 */
@Api(tags = "角色")
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @ApiOperation(value = "角色列表")
    @RequiresPermissions("sys:role:query")
    public PageTableResponse listRoles(PageTableRequest request) {
        return new PageTableHandler(new PageTableHandler.CountHandler() {

            @Override
            public int count(PageTableRequest request) {
                return roleService.count(request.getParams());
            }
        }, new PageTableHandler.ListHandler() {

            @Override
            public List<Role> list(PageTableRequest request) {
                List<Role> list = roleService.list(request.getParams(), request.getOffset(), request.getLimit());
                return list;
            }
        }).handle(request);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据id获取角色")
    @RequiresPermissions("sys:role:query")
    public Role get(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有角色")
    @RequiresPermissions(value = { "sys:user:query", "sys:role:query" }, logical = Logical.OR)
    public List<Role> roles() {
        return roleService.list(Maps.newHashMap(), null, null);
    }

    @GetMapping(params = "userId")
    @ApiOperation(value = "根据用户id获取拥有的角色")
    @RequiresPermissions(value = { "sys:user:query", "sys:role:query" }, logical = Logical.OR)
    public List<Role> roles(Long userId) {
        return roleService.listByUserId(userId);
    }

    @LogAnnotation
    @PostMapping
    @ApiOperation(value = "保存角色")
    @RequiresPermissions("sys:role:add")
    public void saveRole(@RequestBody RoleVO roleVO) {
        roleService.saveRole(roleVO);
    }

    @LogAnnotation
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除角色")
    @RequiresPermissions(value = { "sys:role:del" })
    public void delete(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}

