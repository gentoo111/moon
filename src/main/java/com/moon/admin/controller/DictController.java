package com.moon.admin.controller;

import com.moon.admin.domain.Dict;
import com.moon.admin.service.DictService;
import com.moon.admin.vo.page.PageTableHandler;
import com.moon.admin.vo.page.PageTableRequest;
import com.moon.admin.vo.page.PageTableResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by szz on 2018/4/2 20:07.
 * Email szhz186@gmail.com
 */
@Api("字典")
@RestController
@RequestMapping("/dicts")
public class DictController {

    @Autowired
    private DictService dictService;

    @RequiresPermissions("dict:add")
    @PostMapping
    @ApiOperation("保存")
    public Dict save(@RequestBody Dict dict) {
        Dict d = dictService.getByTypeAndK(dict.getType(), dict.getK());
        if (d != null) {
            throw new IllegalArgumentException("类型和key已存在,不能新增");
        }
        dictService.save(dict);
        return dict;
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取")
    public Dict get(@PathVariable Long id) {
        return dictService.getById(id);
    }


    @RequiresPermissions("dect:add")
    @PutMapping
    @ApiOperation("修改")
    public Dict update(@RequestBody Dict dict) {
        dictService.update(dict);
        return dict;
    }

    @RequiresPermissions("dict:query")
    @GetMapping(params = {"start", "length"})
    @ApiOperation("列表")
    public PageTableResponse list(PageTableRequest request) {
        return new PageTableHandler(new PageTableHandler.CountHandler() {
            @Override
            public int count(PageTableRequest request) {
                return dictService.count(request.getParams());
            }
        }, new PageTableHandler.ListHandler() {
            @Override
            public List<?> list(PageTableRequest request) {
                return dictService.list(request.getParams(),request.getOffset(),request.getLimit());
            }
        }).handle(request);
    }

    @RequiresPermissions("dict:del")
    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public void delete(@PathVariable Long id) {
        dictService.delete(id);
    }

    @GetMapping(params = "type")
    public List<Dict> listByType(String type) {
        return dictService.listByType(type);
    }

}
