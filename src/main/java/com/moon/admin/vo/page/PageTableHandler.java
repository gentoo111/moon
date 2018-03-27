package com.moon.admin.vo.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询处理器
 *
 * Created by szz on 2018/3/27 12:24.
 * Email szhz186@gmail.com
 */
public class PageTableHandler {
    private CountHandler countHandler;
    private ListHandler listHandler;

    public PageTableHandler(CountHandler countHandler, ListHandler listHandler) {
        super();
        this.countHandler = countHandler;
        this.listHandler = listHandler;
    }

    public PageTableResponse handle(PageTableRequest dtRequest) {
        int count = 0;
        List<?> list = null;

        count = this.countHandler.count(dtRequest);
        if (count > 0) {
            list = this.listHandler.list(dtRequest);
        }

        if (list == null) {
            list = new ArrayList<>();
        }

        return new PageTableResponse(count, count, list);
    }

    public interface ListHandler {
        List<?> list(PageTableRequest request);
    }

    public interface CountHandler {
        int count(PageTableRequest request);
    }
}
