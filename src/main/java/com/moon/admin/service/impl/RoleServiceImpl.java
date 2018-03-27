package com.moon.admin.service.impl;

import com.moon.admin.dao.RoleDao;
import com.moon.admin.domain.Role;
import com.moon.admin.service.RoleService;
import com.moon.admin.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by szz on 2018/3/27 12:15.
 * Email szhz186@gmail.com
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public int count(Map<String, Object> params) {
        return roleDao.count(params);
    }

    @Override
    public List<Role> list(Map<String, Object> params, Integer offset, Integer limit) {
        return roleDao.list(params,offset,limit);
    }

    @Override
    public Role getById(Long id) {
        return roleDao.getById(id);
    }

    @Override
    public List<Role> listByUserId(Long userId) {
        return roleDao.listByUserId(userId);
    }

    @Override
    public void delete(Long id) {
        roleDao.delete(id);
    }

    @Override
    public void saveRole(RoleVO roleVO) {
        roleDao.save(roleVO);
    }
}
