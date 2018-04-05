package com.moon.admin.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by szz on 2018/4/5 12:24.
 * Email szhz186@gmail.com
 */
public class GenerateDetail implements Serializable{
    private static final long serialVersionUID = 7571245540854740155L;


    private String beanName;

    private List<BeanField> fields;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public List<BeanField> getFields() {
        return fields;
    }

    public void setFields(List<BeanField> fields) {
        this.fields = fields;
    }
}
