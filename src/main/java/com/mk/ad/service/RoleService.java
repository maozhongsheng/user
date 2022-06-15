package com.mk.ad.service;


import com.mk.ad.entity.SysRole;
import com.mk.ad.vo.resp.PageVO;
import com.mk.ad.vo.req.AddRoleReqVO;
import com.mk.ad.vo.req.RolePageReqVO;
import com.mk.ad.vo.req.RoleUpdateReqVO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: RoleService
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
public interface RoleService {
    PageVO<SysRole> pageInfo(RolePageReqVO vo);
    SysRole addRole(AddRoleReqVO vo);
    List<SysRole> selectAll();
    //SysRole detailInfo(String id);
    void updateRole(RoleUpdateReqVO vo);
    void deletedRole(String roleId);

    List<String> getNamesByUserId(String userId);

    Integer updateStatus(Map params);

    Map detailInfo(String id);

    List<SysRole> selectAllBy();
}
