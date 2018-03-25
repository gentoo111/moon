package com.moon.admin.common.utils;

import com.moon.admin.domain.SysLogs;
import com.moon.admin.service.SysLogService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by szz on 2018/3/24 17:03.
 * Email szhz186@gmail.com
 * 日志处理类
 */
@Aspect
@Component
public class LogAdvice {

    @Autowired
    private SysLogService sysLogService;

    @Around(value = "@annotation(com.moon.admin.common.utils.LogAnnotation)")
    public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
        SysLogs sysLogs = new SysLogs();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String module = null;
        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        module = logAnnotation.module();
        if (StringUtils.isEmpty(module)) {
            ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                module = apiOperation.value();
            }
        }

        if (StringUtils.isEmpty(module)) {
            throw new RuntimeException("没有指定日志module");
        }

        sysLogs.setModule(module);
        try {
            Object object = joinPoint.proceed();
            sysLogs.setFlag(true);
            sysLogService.save(sysLogs);
            return object;
        } catch (Exception e) {
            sysLogs.setFlag(false);
            sysLogs.setRemark(e.getMessage());
            sysLogService.save(sysLogs);
            throw e;
        }

    }
}
