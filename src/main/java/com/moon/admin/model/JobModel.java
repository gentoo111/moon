package com.moon.admin.model;

/**
 * Created by szz on 2018/3/23 23:55.
 */
public class JobModel extends BaseEntity<Long>{
    private static final long serialVersionUID = 5923698917865416603L;

    private String jobName;

    private String description;

    private String cron;

    private String springBeanName;

    private String methodName;

    private Boolean isSysJob;

    private int status;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getSpringBeanName() {
        return springBeanName;
    }

    public void setSpringBeanName(String springBeanName) {
        this.springBeanName = springBeanName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Boolean getIsSysJob() {
        return isSysJob;
    }

    public void setIsSysJob(Boolean isSysJob) {
        this.isSysJob = isSysJob;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
