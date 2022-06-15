package com.mk.ad.mapper;

import com.mk.ad.entity.SysUser;
import com.mk.ad.vo.req.UserPageReqVO;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser getUserInfoByName(String username);

    List<SysUser> selectAll(UserPageReqVO vo);

    int deletedUsers(@Param("sysUser") SysUser sysUser, @Param("list") List<String> list);

    //根据部门id集合查找用户
    List<SysUser> selectUserInfoByDeptIds (List<String> deptIds);

    Integer updateStatus(Map params);
    @Select("select status from admin_ssp_account where id = #{id}")
    String getStatus(String id);
    @Select("select status from admin_dsp_account where id = #{id}")
    String getDSPStatus(String id);

    List selectemail();

    Integer getDspType(String id);

    Map selectCompanyProfile(String id);
}