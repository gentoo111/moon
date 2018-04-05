package com.moon.admin.vo;

import com.moon.admin.domain.Notice;
import com.moon.admin.domain.User;

import java.io.Serializable;
import java.util.List;

/**
 * Created by szz on 2018/4/5 12:27.
 * Email szhz186@gmail.com
 */
public class NoticeVO implements Serializable{

    private static final long serialVersionUID = -938974811148699054L;
    private Notice notice;

    private List<User> users;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
