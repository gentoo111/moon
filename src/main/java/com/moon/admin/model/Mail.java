package com.moon.admin.model;

/**
 * Created by szz on 2018/3/23 23:40.
 */
public class Mail extends BaseEntity<Long>{
    private static final long serialVersionUID = 4196930995732976135L;
    private Long userId;
    private String toUsers;
    private String subject;
    private String content;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToUsers() {
        return toUsers;
    }

    public void setToUsers(String toUsers) {
        this.toUsers = toUsers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
