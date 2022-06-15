package com.mk.ad.service.impl;

import com.github.pagehelper.PageHelper;
import com.mk.ad.entity.SysLog;
import com.mk.ad.exception.BusinessException;
import com.mk.ad.mapper.SysLogMapper;
import com.mk.ad.service.LogService;
import com.mk.ad.utils.PageUtil;
import com.mk.ad.vo.resp.PageVO;
import com.mk.ad.exception.code.BaseResponseCode;
import com.mk.ad.vo.req.SysLogPageReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: LogServiceImpl
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    @Override
    public PageVO<SysLog> pageInfo(SysLogPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        return PageUtil.getPageVO(sysLogMapper.selectAll(vo));
    }

    @Override
    public void deletedLog(List<String> logIds) {
        int i = sysLogMapper.batchDeletedLog(logIds);
        if(i==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }
}
