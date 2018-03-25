package com.moon.admin.domain;

import java.util.Date;

/**
 * Created by szz on 2018/3/23 23:54.
 */
public class WoWorksheet extends BaseEntity<Long>{
    private static final long serialVersionUID = 7822110730059815796L;

    private String workNo;
    private String snNo;
    private String woNo;
    private Integer pmId;
    private Integer engineerId;
    private String woClassifyMain;
    private String woClassifyDetail;
    private String woStatus;
    private String woType;
    private String serviceType;
    private String service;
    private String serviceStation;
    private String woGrade;
    private String woClient;
    private String woClientStation;
    private String woDeviceId;
    private String description;
    private String projectId;
    private String templateId;
    private Date advComTime;
    private Date totalCostTime;
    private Date beginTime;
    private String isIndependent;
    private String assitBy;
    private String needAssitReason;
    private String remarks;

    public String getWorkNo() {
        return workNo;
    }
    public String setWorkNo() {
        return workNo;
    }
    public String getSnNo() {
        return snNo;
    }
    public String setSnNo() {
        return snNo;
    }
    public String getWoNo() {
        return woNo;
    }
    public String setWoNo() {
        return woNo;
    }
    public Integer getPmId() {
        return pmId;
    }
    public Integer setPmId() {
        return pmId;
    }
    public Integer getEngineerId() {
        return engineerId;
    }
    public Integer setEngineerId() {
        return engineerId;
    }
    public String getWoClassifyMain() {
        return woClassifyMain;
    }
    public String setWoClassifyMain() {
        return woClassifyMain;
    }
    public String getWoClassifyDetail() {
        return woClassifyDetail;
    }
    public String setWoClassifyDetail() {
        return woClassifyDetail;
    }
    public String getWoStatus() {
        return woStatus;
    }
    public String setWoStatus() {
        return woStatus;
    }
    public String getWoType() {
        return woType;
    }
    public String setWoType() {
        return woType;
    }
    public String getServiceType() {
        return serviceType;
    }
    public String setServiceType() {
        return serviceType;
    }
    public String getService() {
        return service;
    }
    public String setService() {
        return service;
    }
    public String getServiceStation() {
        return serviceStation;
    }
    public String setServiceStation() {
        return serviceStation;
    }
    public String getWoGrade() {
        return woGrade;
    }
    public String setWoGrade() {
        return woGrade;
    }
    public String getWoClient() {
        return woClient;
    }
    public String setWoClient() {
        return woClient;
    }
    public String getWoClientStation() {
        return woClientStation;
    }
    public String setWoClientStation() {
        return woClientStation;
    }
    public String getWoDeviceId() {
        return woDeviceId;
    }
    public String setWoDeviceId() {
        return woDeviceId;
    }
    public String getDescription() {
        return description;
    }
    public String setDescription() {
        return description;
    }
    public String getProjectId() {
        return projectId;
    }
    public String setProjectId() {
        return projectId;
    }
    public String getTemplateId() {
        return templateId;
    }
    public String setTemplateId() {
        return templateId;
    }
    public Date getAdvComTime() {
        return advComTime;
    }
    public Date setAdvComTime() {
        return advComTime;
    }
    public Date getTotalCostTime() {
        return totalCostTime;
    }
    public Date setTotalCostTime() {
        return totalCostTime;
    }
    public Date getBeginTime() {
        return beginTime;
    }
    public Date setBeginTime() {
        return beginTime;
    }
    public String getIsIndependent() {
        return isIndependent;
    }
    public String setIsIndependent() {
        return isIndependent;
    }
    public String getAssitBy() {
        return assitBy;
    }
    public String setAssitBy() {
        return assitBy;
    }
    public String getNeedAssitReason() {
        return needAssitReason;
    }
    public String setNeedAssitReason() {
        return needAssitReason;
    }
    public String getRemarks() {
        return remarks;
    }
    public String setRemarks() {
        return remarks;
    }
}
