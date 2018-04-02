package com.moon.admin.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.moon.admin.common.utils.SpringUtil;
import com.moon.admin.common.utils.UserUtil;
import com.moon.admin.domain.User;
import com.moon.admin.service.SysLogService;
import com.moon.admin.service.TokenManager;
import com.moon.admin.vo.ResponseInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by szz on 2018/3/30 14:46.
 * Email szhz186@gmail.com
 * web退出和restful方式退出<br>
 * 后者会删除缓存的token
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter{

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        String loginToken = RestfulFilter.getToken(request);
        User user = UserUtil.getCurrentUser();
        if (StringUtils.isBlank(loginToken)) {// 非Restful方式
            boolean flag = super.preHandle(request, response);
            log.debug("{}退出成功", user.getUsername());
            SpringUtil.getBean(SysLogService.class).save(user.getId(), "退出", true, null);

            return flag;
        } else {
            TokenManager tokenManager = SpringUtil.getBean(TokenManager.class);
            boolean flag = tokenManager.deleteToken(loginToken);
            if (flag) {
                RestfulFilter.writeResponse(WebUtils.toHttp(response), HttpStatus.OK.value(), SUCCESS_INFO);
                log.debug("{}退出成功", user.getUsername());
            } else {
                RestfulFilter.writeResponse(WebUtils.toHttp(response), HttpStatus.BAD_REQUEST.value(), ERR_INFO);
            }

            SpringUtil.getBean(SysLogService.class).save(user.getId(), "token方式退出", flag, null);

            return false;
        }
    }

    private static String SUCCESS_INFO = JSONObject.toJSONString(new ResponseInfo(HttpStatus.OK.value() + "", "退出成功"));
    private static String ERR_INFO = JSONObject
            .toJSONString(new ResponseInfo(HttpStatus.BAD_REQUEST.value() + "", "退出失败,token不存在"));
}
