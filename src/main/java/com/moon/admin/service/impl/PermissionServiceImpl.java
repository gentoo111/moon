package com.moon.admin.service.impl;

import com.moon.admin.dao.PermissionDao;
import com.moon.admin.domain.Permission;
import com.moon.admin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by szz on 2018/3/26 23:42.
 * Email szhz186@gmail.com
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> listByUserId(Long id) {
        return permissionDao.listByUserId(id);
    }

    @Override
    public List<Permission> listAll() {
        return permissionDao.listAll();
    }

    @Override
    public List<Permission> listParents() {
        return permissionDao.listParents();
    }

    @Override
    public List<Permission> listByRoleId(Long roleId) {
        return permissionDao.listByRoleId(roleId);
    }

    @Override
    public void save(Permission permission) {
        permissionDao.save(permission);
    }

    @Override
    public Permission getById(Long id) {
        return permissionDao.getById(id);
    }

    @Override
    public void update(Permission permission) {
        permissionDao.update(permission);
    }

    @Override
    public void delete(Long id) {
        permissionDao.delete(id);
    }


}
