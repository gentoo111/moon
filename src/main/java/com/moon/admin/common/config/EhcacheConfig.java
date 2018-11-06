package com.moon.admin.common.config;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by szz on 2018/3/25 22:59.
 * Email szhz186@gmail.com
 */
@Configuration
public class EhcacheConfig {

    @Bean("ehCacheManager")
    public EhCacheManager cacheManager(){
        EhCacheManager cacheManager=new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }
}
