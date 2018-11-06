package com.moon.admin.vo.page;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页查询参数
 *
 * Created by szz on 2018/3/27 12:25.
 * Email szhz186@gmail.com
 */
public class PageTableRequest implements Serializable{


    private static final long serialVersionUID = 4087463800262783346L;
    private Integer offset;
    private Integer limit;
    private Map<String, Object> params;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
