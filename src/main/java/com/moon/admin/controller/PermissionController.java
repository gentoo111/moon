package com.moon.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.moon.admin.common.utils.LogAnnotation;
import com.moon.admin.common.utils.UserUtil;
import com.moon.admin.domain.Permission;
import com.moon.admin.domain.User;
import com.moon.admin.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by szz on 2018/3/26 23:32.
 * Email szhz186@gmail.com
 */
@Api(tags = "权限")
@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @ApiOperation(value = "当前登录用户拥有的权限")
    @GetMapping("/current")
    public List<Permission> permissionCurrent() {
        List<Permission> currentPermission = UserUtil.getCurrentPermissions();
        if (currentPermission == null) {
            User user = UserUtil.getCurrentUser();
            currentPermission = permissionService.listByUserId(user.getId());
            UserUtil.setPermissionSession(currentPermission);
        }

        final List<Permission> permissions = currentPermission.stream().filter(l -> l.getType().equals(1)).collect(Collectors.toList());
        setChild(permissions);
        return permissions.stream().filter(p -> p.getParentId().equals(0L)).collect(Collectors.toList());
    }

    private void setChild(List<Permission> permissions) {
        permissions.parallelStream().forEach(per->{
            List<Permission> child = permissions.stream().filter(p -> p.getParentId().equals(per.getId())).collect(Collectors.toList());
            per.setChild(child);
        });
    }

    @ApiOperation(value = "菜单列表")
    @GetMapping
    @RequiresPermissions("sys:menu:query")
    public List<Permission> permissions(){
        List<Permission> permissionAll=permissionService.listAll();

        List<Permission> list= Lists.newArrayList();
        setPermissionList(0L,permissionAll,list);

        return list;
    }

    private void setPermissionList(Long pId, List<Permission> permissionAll, List<Permission> list) {
        for (Permission permission : permissionAll) {
            if (permission.getParentId().equals(pId)){
                list.add(permission);
                if (permissionAll.stream().filter(p->p.getParentId().equals(permission.getId())).findAny()!=null){
                    setPermissionList(permission.getId(),permissionAll,list);
                }
            }
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "所有菜单")
    @RequiresPermissions("sys:menu:query")
    public JSONArray permissionAll(){
        List<Permission> permissionAll = permissionService.listAll();
        JSONArray array = new JSONArray();
        setPermissionTree(0L,permissionAll,array);
        return array;
    }

    /**
     * 菜单树,通过权限表生成菜单,考虑单独做一张menu表来完成
     * @param pId
     * @param permissionAll
     * @param array
     */
    private void setPermissionTree(Long pId, List<Permission> permissionAll, JSONArray array) {
        for (Permission permission : permissionAll) {
            if (permission.getParentId().equals(pId)){
                String string = JSONObject.toJSONString(permission);
                JSONObject parent= (JSONObject) JSONObject.parse(string);
                array.add(parent);

                if (permissionAll.stream().filter(p->p.getParentId().equals(permission.getId())).findAny()!=null){
                    JSONArray child = new JSONArray();
                    parent.put("child",child);
                    setPermissionTree(permission.getId(),permissionAll,child);
                }
            }
        }
    }

    @GetMapping("/parents")
    @ApiOperation(value = "一级菜单")
    @RequiresPermissions("sys:menu:query")
    public List<Permission> parentMenu(){
        return permissionService.listParents();
    }

    @GetMapping(params = "roleId")
    @ApiOperation(value = "根据角色id获取权限")
    @RequiresPermissions(value = {"sys:menu:query","sys:role:query"},logical = Logical.OR)
    public List<Permission> listByRoleId(Long roleId){
        return permissionService.listByRoleId(roleId);
    }

    @LogAnnotation
    @PostMapping
    @ApiOperation(value = "保存菜单")
    @RequiresPermissions("sys:menu:add")
    public void save(@RequestBody Permission permission){
        permissionService.save(permission);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据菜单id获取菜单")
    @RequiresPermissions("sys:menu:query")
    public Permission getById(@PathVariable Long id){
        return permissionService.getById(id);
    }

    @LogAnnotation
    @PutMapping
    @ApiOperation(value = "修改菜单")
    @RequiresPermissions("sys:menu:add")
    public void update(@RequestBody Permission permission){
        permissionService.update(permission);
    }

    @GetMapping("/owns")
    @ApiOperation(value = "校验当前用户的权限")
    public Set<String> ownsPermission(){
        List<Permission> permissions = UserUtil.getCurrentPermissions();
        if (CollectionUtils.isEmpty(permissions)){
            return Collections.emptySet();
        }

        return permissions.parallelStream().filter(p->!StringUtils.isEmpty(p.getPermission())).map(Permission::getPermission).collect(Collectors.toSet());
    }

    @LogAnnotation
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除菜单")
    @RequiresPermissions("sys:menu:del")
    public void delete(@PathVariable Long id){
        permissionService.delete(id);
    }
}
