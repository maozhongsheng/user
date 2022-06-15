package com.mk.ad.service;


import com.mk.ad.entity.SysLog;
import com.mk.ad.vo.resp.PageVO;
import com.mk.ad.vo.req.SysLogPageReqVO;

import java.util.List;

/**
 * @ClassName: LogService
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
public interface LogService {

    PageVO<SysLog> pageInfo(SysLogPageReqVO vo);

    void deletedLog(List<String> logIds);
}
