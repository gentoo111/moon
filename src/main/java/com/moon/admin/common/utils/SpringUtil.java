package com.moon.admin.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Created by szz on 2018/3/25 23:05.
 * Email szhz186@gmail.com
 * 通过spring获取bean,shiro的realm并没有交给spring管理,所以需要手动注入
 */
@Component
public class SpringUtil implements ApplicationContextAware{

    private static ApplicationContext applicationContext=null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext=applicationContext;
    }

    public static <T> T getBean(Class<T> cls){
        return applicationContext.getBean(cls);
    }

    public static <T> T getBean(String name,Class<T> cls){
        return applicationContext.getBean(name,cls);
    }

    public static String getProperty(String key){
        return applicationContext.getBean(Environment.class).getProperty(key);
    }
}
