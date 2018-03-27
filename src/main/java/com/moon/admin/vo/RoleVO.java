package com.moon.admin.vo;

import com.moon.admin.domain.Role;

import java.util.List;

/**
 * Created by szz on 2018/3/27 13:34.
 * Email szhz186@gmail.com
 */
public class RoleVO extends Role{
    private static final long serialVersionUID = 3164658000727722430L;

    private List<Long> permissionIds;

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
