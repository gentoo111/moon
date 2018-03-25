package com.moon.admin.domain;

/**
 * Created by szz on 2018/3/23 23:35.
 */
public class Dict extends BaseEntity<Long>{
    private static final long serialVersionUID = -259557412831896966L;
    private String type;
    private String k;
    private String val;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
