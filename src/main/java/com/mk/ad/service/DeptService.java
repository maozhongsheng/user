package com.mk.ad.service;

import com.mk.ad.entity.SysDept;
import com.mk.ad.vo.resp.DeptRespNodeVO;
import com.mk.ad.vo.req.DeptAddReqVO;
import com.mk.ad.vo.req.DeptUpdateReqVO;

import java.util.List;

/**
 * @ClassName: DeptService
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
public interface DeptService {

    List<SysDept> selectAll();

    List<DeptRespNodeVO> deptTreeList(String deptId);

    SysDept addDept(DeptAddReqVO vo);

    void updateDept(DeptUpdateReqVO vo);

    void deletedDept(String id);

}
