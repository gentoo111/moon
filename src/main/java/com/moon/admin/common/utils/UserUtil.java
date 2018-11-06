package com.moon.admin.common.utils;

import com.moon.admin.common.constants.UserConstants;
import com.moon.admin.domain.Permission;
import com.moon.admin.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * Created by szz on 2018/3/24 17:27.
 * Email szhz186@gmail.com
 */
@SuppressWarnings("all")
public class UserUtil {
    public static User getCurrentUser() {
        User user = (User) getSession().getAttribute(UserConstants.LOGIN_USER);

        return user;
    }

    public static void setUserSession(User user) {
        getSession().setAttribute(UserConstants.LOGIN_USER, user);
    }


    public static List<Permission> getCurrentPermissions() {
        List<Permission> list = (List<Permission>) getSession().getAttribute(UserConstants.USER_PERMISSIONS);

        return list;
    }

    public static void setPermissionSession(List<Permission> list) {
        getSession().setAttribute(UserConstants.USER_PERMISSIONS, list);
    }

    public static Session getSession() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();

        return session;
    }
}
