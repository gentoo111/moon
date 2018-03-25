package com.moon.admin.dao;

import com.moon.admin.domain.SysLogs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * Created by szz on 2018/3/23 23:08.
 */
@Mapper
public interface SysLogsDao {

	@Insert("insert into sys_logs(userId, module, flag, remark, createTime) values(#{user.id}, #{module}, #{flag}, #{remark}, now())")
	int save(SysLogs sysLogs);

	int count(@Param("params") Map<String, Object> params);

	List<SysLogs> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset,
                       @Param("limit") Integer limit);

	@Delete("delete from sys_logs where createTime <= #{time}")
	int deleteLogs(String time);
}
