package com.moon.admin.utils;

import com.moon.admin.constants.UserConstants;
import com.moon.admin.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * Created by szz on 2018/3/24 17:27.
 * Email szhz186@gmail.com
 */
public class UserUtil {

    public static User getCurrentUser() {
       return  (User)getSession().getAttribute(UserConstants.LOGIN_USER);
    }

    public static void serUserSession(User user){
        getSession().setAttribute(UserConstants.LOGIN_TOKEN,user);
    }

    @SuppressWarnings("all")
    public static List<Permission> getCurrentPermission(){
        return (List<Permission>) getSession().getAttribute(UserConstants.USER_PERMISSIONS);
    }

    public static void setPermissionSession(List<Permission> permissions){
        getSession().setAttribute(UserConstants.USER_PERMISSIONS,permissions);
    }

    public static Session getSession(){
        Subject subject =SecurityUtils.getSubject();
        Session session = subject.getSession();
        return session;
    }
}
