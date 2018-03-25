package com.moon.admin.dao;

import com.moon.admin.domain.WoWorksheet;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
/**
 * Created by szz on 2018/3/23 23:08.
 */
@Mapper
public interface WoWorksheetDao {

    @Select("select * from wo_worksheet t where t.id = #{id}")
    WoWorksheet getById(Long id);

    @Delete("delete from wo_worksheet where id = #{id}")
    int delete(Long id);

    int update(WoWorksheet woWorksheet);
    
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into wo_worksheet(workNo, snNo, woNo, pmId, engineerId, woClassifyMain, woClassifyDetail, woStatus, woType, serviceType, service, serviceStation, woGrade, woClient, woClientStation, woDeviceId, description, projectId, templateId, advComTime, totalCostTime, beginTime, isIndependent, assitBy, needAssitReason, remarks) values(#{workNo}, #{snNo}, #{woNo}, #{pmId}, #{engineerId}, #{woClassifyMain}, #{woClassifyDetail}, #{woStatus}, #{woType}, #{serviceType}, #{service}, #{serviceStation}, #{woGrade}, #{woClient}, #{woClientStation}, #{woDeviceId}, #{description}, #{projectId}, #{templateId}, #{advComTime}, #{totalCostTime}, #{beginTime}, #{isIndependent}, #{assitBy}, #{needAssitReason}, #{remarks})")
    int save(WoWorksheet woWorksheet);
    
    int count(@Param("params") Map<String, Object> params);

    List<WoWorksheet> list(@Param("params") Map<String, Object> params, @Param("offset") Integer offset, @Param("limit") Integer limit);
}
