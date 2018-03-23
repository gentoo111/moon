package com.moon.admin.model;

/**
 * Created by szz on 2018/3/23 23:43.
 */
public class MailTo extends BaseEntity<Long> {

    private static final long serialVersionUID = -3331918445634521488L;

    private Long mailId;
    private String toUser;
    private Boolean status;

    public Long getMailId() {
        return mailId;
    }

    public void setMailId(Long mailId) {
        this.mailId = mailId;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
