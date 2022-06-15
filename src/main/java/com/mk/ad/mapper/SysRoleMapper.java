package com.mk.ad.mapper;

import com.mk.ad.entity.SysRole;
import com.mk.ad.vo.req.RolePageReqVO;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    List<SysRole> selectAll(RolePageReqVO vo);

    List<String> selectNamesByIds(List<String> ids);

    Integer UpdateStatus(Map params);

    SysRole getUserInfoByName(String username);

    List<SysRole> selectAllBy(RolePageReqVO rolePageReqVO);
}