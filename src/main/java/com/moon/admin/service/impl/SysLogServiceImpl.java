package com.moon.admin.service.impl;

import com.moon.admin.dao.SysLogsDao;
import com.moon.admin.model.SysLogs;
import com.moon.admin.model.User;
import com.moon.admin.service.SysLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moon.admin.utils.UserUtil;
/**
 * Created by szz on 2018/3/24 17:05.
 * Email szhz186@gmail.com
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    private static final Logger LOGGER= LoggerFactory.getLogger("adminLogger");

    @Autowired
    private SysLogsDao sysLogsDao;

    @Override
    public void save(SysLogs sysLogs) {
        User user= UserUtil.getCurrentUser();
        if (user==null||user.getId()==null){
            return;
        }
        sysLogs.setUser(user);
        sysLogsDao.save(sysLogs);

    }
}
