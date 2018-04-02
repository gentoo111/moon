package com.moon.admin.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Created by szz on 2018/3/30 15:08.
 * Email szhz186@gmail.com
 */
@Configuration
public class JobConfig {
    public static final String KEY = "applicationContextSchedulerContextKey";

    @Bean("adminQuartzScheduler")
    public SchedulerFactoryBean quartzScheduler(DataSource dataSource) {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

        try {
            quartzScheduler.setQuartzProperties(
                    PropertiesLoaderUtils.loadProperties(new ClassPathResource("quartz.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        quartzScheduler.setDataSource(dataSource);
        quartzScheduler.setOverwriteExistingJobs(true);
        quartzScheduler.setApplicationContextSchedulerContextKey(KEY);
        quartzScheduler.setStartupDelay(10);

        return quartzScheduler;
    }
}
