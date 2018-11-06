package com.moon.admin.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by szz on 2018/3/23 23:08.
 */
public abstract class BaseEntity<ID extends Serializable> implements Serializable{

    private static final long serialVersionUID = -6836765691831124161L;

    private ID id;
    private Date createTime=new Date();
    private Date updateTime=new Date();

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
