package com.moon.admin.common.job;

import com.moon.admin.common.config.JobConfig;
import com.moon.admin.service.JobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by szz on 2018/3/30 15:15.
 * Email szhz186@gmail.com
 */
public class SpringBeanJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext()
                    .get(JobConfig.KEY);
            JobService jobService = applicationContext.getBean(JobService.class);
            jobService.doJob(context.getJobDetail().getJobDataMap());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
