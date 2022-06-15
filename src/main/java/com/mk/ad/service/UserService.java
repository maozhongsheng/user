package com.mk.ad.service;

import com.mk.ad.entity.SysUser;
import com.mk.ad.vo.req.*;
import com.mk.ad.vo.resp.PageVO;
import com.mk.ad.vo.resp.LoginRespVO;
import com.mk.ad.vo.resp.UserInfoRespVO;
import com.mk.ad.vo.resp.UserOwnRoleRespVO;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserService
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
public interface UserService {

    LoginRespVO login(LoginReqVO vo);


    PageVO<SysUser> pageInfo(UserPageReqVO vo);

    void addUser(UserAddReqVO vo);

    UserOwnRoleRespVO getUserOwnRole(String userId);

    void setUserOwnRole(UserOwnRoleReqVO vo);

    String refreshToken(String refreshToken);

    void updateUserInfo(UserUpdateReqVO vo,String operationId);

    void deletedUsers(List<String> list, String operationId);

    List<SysUser> selectUserInfoByDeptIds(List<String> deptIds);

    SysUser detailInfo(String userId);

    //个人用户编辑信息接口
    void userUpdateDetailInfo(UserUpdateDetailInfoReqVO vo, String userId);

    void userUpdatePwd(UserUpdatePwdReqVO vo, String accessToken, String refreshToken);

    void logout(String accessToken,String refreshToken);

    void userUpdatePwdssp(UserUpdateInfoReqVO vo, String accessToken, String refresgToken);

    void userUpdateDetailInfossp(UserUpdateInfoReqVO vo, String userId);

    UserInfoRespVO getusersByid(UserPageReqVO vo);


    Integer updateStatus(Map params);
}
