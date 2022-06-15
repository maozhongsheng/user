package com.mk.ad.mapper;


import com.mk.ad.entity.SysLog;
import com.mk.ad.vo.req.SysLogPageReqVO;

import java.util.List;


public interface SysLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    List<SysLog> selectAll(SysLogPageReqVO vo);


    int batchDeletedLog(List<String> logIds);


}