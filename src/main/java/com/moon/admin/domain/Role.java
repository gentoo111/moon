package com.moon.admin.domain;

/**
 * Created by szz on 2018/3/23 23:50.
 */
public class Role extends BaseEntity<Long> {
    private static final long serialVersionUID = 9039214904288692466L;

    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
