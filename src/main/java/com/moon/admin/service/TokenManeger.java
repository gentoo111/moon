package com.moon.admin.service;

import com.moon.admin.vo.Token;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by szz on 2018/3/25 22:42.
 * Email szhz186@gmail.com
 *
 * Token管理器
 * 默认基于ehcache,使用@Primary注解切换
 *
 * @see EhCacheTokenManager
 * @see RedisTokenManager
 */
public interface TokenManeger {

    Token saveToken(UsernamePasswordToken token);

    UsernamePasswordToken getToken(String key);

    boolean deleteToken(String key);
}
