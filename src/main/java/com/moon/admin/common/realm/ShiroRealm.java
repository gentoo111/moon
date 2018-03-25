package com.moon.admin.common.realm;

import com.moon.admin.common.utils.SpringUtil;
import com.moon.admin.common.utils.UserUtil;
import com.moon.admin.domain.User;
import com.moon.admin.domain.User.Status;
import com.moon.admin.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by szz on 2018/3/25 21:35.
 * Email szhz186@gmail.com
 */
public class ShiroRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger("adminLogger");


    /**
     * 登陆
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;

        String username = usernamePasswordToken.getUsername();
        UserService userService = SpringUtil.getBean(UserService.class);

        User user = userService.getUser(username);
        if (user == null) {
            throw new UnknownAccountException("用户名不存在");
        }

        if (!user.getPassword().equals(userService.passwordEncoder(new String(usernamePasswordToken.getPassword()), user.getSalt()))) {
            throw new IncorrectCredentialsException("密码错误");
        }

        if (user.getStatus() != Status.VALID) {
            throw new IncorrectCredentialsException("无效状态,请与管理员联系");
        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        UserUtil.setUserSession(user);

        return authenticationInfo;
    }

    /**
     * 权限校验
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }


}
