package com.moon.admin.service.impl;

import com.moon.admin.dao.DictDao;
import com.moon.admin.domain.Dict;
import com.moon.admin.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by szz on 2018/4/2 20:12.
 * Email szhz186@gmail.com
 */
@Service
public class DictServiceImpl implements DictService {

    @Autowired
    private DictDao dictDao;

    @Override
    public Dict getByTypeAndK(String type, String k) {
        return dictDao.getByTypeAndK(type,k);
    }

    @Override
    public void save(Dict dict) {
        dictDao.save(dict);
    }

    @Override
    public Dict getById(Long id) {
        return dictDao.getById(id);
    }

    @Override
    public void update(Dict dict) {
        dictDao.update(dict);
    }

    @Override
    public void delete(Long id) {
        dictDao.delete(id);
    }

    @Override
    public List<Dict> listByType(String type) {
        return dictDao.listByType(type);
    }

    @Override
    public int count(Map<String, Object> params) {
        return dictDao.count(params);
    }

    @Override
    public List<Dict> list(Map<String, Object> params, Integer offset, Integer limit) {
        return dictDao.list(params,offset,limit);
    }

}
