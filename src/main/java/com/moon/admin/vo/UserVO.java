package com.moon.admin.vo;

import com.moon.admin.domain.User;

import java.util.List;

/**
 * Created by szz on 2018/4/5 12:29.
 * Email szhz186@gmail.com
 */
public class UserVO extends User{

    private static final long serialVersionUID = 4613848879669316299L;
    private List<Long> roleIds;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
