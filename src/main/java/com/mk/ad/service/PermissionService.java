package com.mk.ad.service;


import com.mk.ad.entity.SysPermission;
import com.mk.ad.vo.req.PermissionAddReqVO;
import com.mk.ad.vo.req.PermissionUpdateReqVO;
import com.mk.ad.vo.resp.PermissionRespNodeVO;

import java.util.List;

/**
 * @ClassName: PermissionService
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
public interface PermissionService {
    List<SysPermission> selectAll();
    List<PermissionRespNodeVO> selectAllMenuByTree();
    SysPermission addPermission(PermissionAddReqVO vo);
    List<PermissionRespNodeVO> permissionTreeList(String userId);
    List<PermissionRespNodeVO> selectAllTree();
    void updatePermission(PermissionUpdateReqVO vo);
    void deletedPermission(String permissionId);
    List<String> getPermissionByUserId(String userId);
    List<SysPermission> getPermissions(String userId);
}
