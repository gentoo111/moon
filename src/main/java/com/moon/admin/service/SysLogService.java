package com.moon.admin.service;

import com.moon.admin.domain.SysLogs;

/**
 * Created by szz on 2018/3/24 17:05.
 * Email szhz186@gmail.com
 */
public interface SysLogService {
    void save(SysLogs sysLogs);

    void save(Long userId, String module, Boolean flag, String remark);

    void deleteLogs();
}
