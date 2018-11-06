package com.moon.admin.service;

import com.moon.admin.domain.Permission;

import java.util.List;

/**
 * Created by szz on 2018/3/26 23:41.
 * Email szhz186@gmail.com
 */
public interface PermissionService {

    List<Permission> listByUserId(Long id);

    List<Permission> listAll();

    List<Permission> listParents();

    List<Permission> listByRoleId(Long roleId);

    void save(Permission permission);

    Permission getById(Long id);

    void update(Permission permission);

    void delete(Long id);
}
