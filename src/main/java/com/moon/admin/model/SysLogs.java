package com.moon.admin.model;

/**
 * Created by szz on 2018/3/23 23:50.
 */
public class SysLogs extends BaseEntity<Long> {
    private static final long serialVersionUID = -277015974643775825L;

    private User user;
    private String module;
    private Boolean flag;
    private String remark;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
