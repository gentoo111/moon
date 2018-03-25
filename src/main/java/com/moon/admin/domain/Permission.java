package com.moon.admin.domain;

import java.util.List;

/**
 * Created by szz on 2018/3/23 23:48.
 */
public class Permission extends BaseEntity<Long> {
    private static final long serialVersionUID = 16004680504863310L;

    private Long parentId;
    private String name;
    private String css;
    private String href;
    private Integer type;
    private String permission;
    private Integer sort;

    private List<Permission> child;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<Permission> getChild() {
        return child;
    }

    public void setChild(List<Permission> child) {
        this.child = child;
    }
}
