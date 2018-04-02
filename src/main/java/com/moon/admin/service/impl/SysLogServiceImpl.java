package com.moon.admin.service.impl;

import com.moon.admin.dao.SysLogsDao;
import com.moon.admin.domain.SysLogs;
import com.moon.admin.domain.User;
import com.moon.admin.service.SysLogService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.moon.admin.common.utils.UserUtil;

import java.util.Date;

/**
 * Created by szz on 2018/3/24 17:05.
 * Email szhz186@gmail.com
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    private static final Logger log = LoggerFactory.getLogger("adminLogger");

    @Autowired
    private SysLogsDao sysLogsDao;

    @Override
    public void save(SysLogs sysLogs) {
        User user = UserUtil.getCurrentUser();
        if (user == null || user.getId() == null) {
            return;
        }

        sysLogs.setUser(user);
        sysLogsDao.save(sysLogs);
    }

    @Async
    @Override
    public void save(Long userId, String module, Boolean flag, String remark) {
        SysLogs sysLogs = new SysLogs();
        sysLogs.setFlag(flag);
        sysLogs.setModule(module);
        sysLogs.setRemark(remark);

        User user = new User();
        user.setId(userId);
        sysLogs.setUser(user);

        sysLogsDao.save(sysLogs);

    }

    @Override
    public void deleteLogs() {
        Date date = DateUtils.addMonths(new Date(), -3);
        String time = DateFormatUtils.format(date, DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.getPattern());

        int n = sysLogsDao.deleteLogs(time);
        log.info("删除{}之前日志{}条", time, n);
    }
}
