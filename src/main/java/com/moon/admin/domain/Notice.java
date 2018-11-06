package com.moon.admin.domain;

/**
 * Created by szz on 2018/3/23 23:45.
 */
public class Notice extends BaseEntity<Long>{
    private static final long serialVersionUID = -5990334491457853974L;

    private String title;
    private String content;
    private Integer status;

    public interface Status{
        int DRAFT=0;
        int PUBLISH=1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
