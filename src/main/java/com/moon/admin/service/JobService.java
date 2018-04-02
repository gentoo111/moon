package com.moon.admin.service;

import com.moon.admin.domain.JobModel;
import org.quartz.JobDataMap;
import org.quartz.SchedulerException;

/**
 * Created by szz on 2018/3/30 15:16.
 * Email szhz186@gmail.com
 */
public interface JobService {
    void saveJob(JobModel jobModel);

    void doJob(JobDataMap jobDataMap);

    void deleteJob(Long id) throws SchedulerException;
}
