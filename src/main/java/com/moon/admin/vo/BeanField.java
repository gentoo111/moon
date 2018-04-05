package com.moon.admin.vo;

import java.io.Serializable;

/**
 * Created by szz on 2018/4/5 12:24.
 * Email szhz186@gmail.com
 */
public class BeanField implements Serializable{
    private static final long serialVersionUID = 3102040362133083037L;

    private String columnName;

    private String columnType;

    private String columnComment;

    private String columnDefault;

    private String name;

    private String type;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
