package com.mk.ad.controller;

import com.mk.ad.constants.Constant;
import com.mk.ad.entity.SysUser;
import com.mk.ad.service.UserService;
import com.mk.ad.utils.DataResult;
import com.mk.ad.utils.JWTHelper;
import com.mk.ad.utils.JwtTokenUtil;
import com.mk.ad.vo.req.*;
import com.mk.ad.vo.resp.PageVO;
import com.mk.ad.vo.resp.LoginRespVO;
import com.mk.ad.vo.resp.UserOwnRoleRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserController
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "组织管理-用户管理",description = "用户模块相关接口")
@Slf4j
@CrossOrigin(value = "*", maxAge = 3600, allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo){
        DataResult result=DataResult.success();
        result.setData(userService.login(vo));
        log.info(result.toString());
        return result;
    }

    @PostMapping("/usersByid")
    @ApiOperation(value = "查询用户详情")
    //@RequiresPermissions("sys:user:list")
    public DataResult getusersByid(@RequestBody UserPageReqVO vo){
        DataResult result=DataResult.success();
        result.setData(userService.getusersByid(vo));
        return result;
    }
    @PostMapping("/usersStatus")
    @ApiOperation(value = "修改账户状态")
    //@RequiresPermissions("sys:user:list")
    public DataResult updateStatus(@RequestBody UserPageReqVO vo){
        DataResult result=null;
        Map params = new HashMap();
        String id = vo.getId();
        String status = vo.getStatus().toString();
        params.put("id",id);
        params.put("status",status);
        Integer results =userService.updateStatus(params);
        if(results==1){
             result=DataResult.success();
        }
        return result;
    }

    @PostMapping("/users")
    @ApiOperation(value = "分页查询用户接口")
   // @RequiresPermissions("sys:user:list")
    public DataResult<PageVO<SysUser>> pageInfo(@RequestBody UserPageReqVO vo){
        DataResult result=DataResult.success();
        result.setData(userService.pageInfo(vo));
        return result;
    }

    @PostMapping("/user/addUser")
    @ApiOperation(value = "新增用户接口")
    //@RequiresPermissions("sys:user:add")
    public DataResult addUser(@RequestBody @Valid UserAddReqVO vo){
        DataResult result=DataResult.success();
        userService.addUser(vo);
        return result;
    }


    @GetMapping("/user/roles/{userId}")
    @ApiOperation(value = "查询用户拥有的角色数据接口")
    //@RequiresPermissions("sys:user:role:update")
    public DataResult<UserOwnRoleRespVO> getUserOwnRole(@PathVariable("userId") String userId){
        DataResult result=DataResult.success();
        result.setData(userService.getUserOwnRole(userId));
        return result;
    }

    @PutMapping("/user/roles")
    @ApiOperation(value = "保存用户拥有的角色信息接口")
    //@RequiresPermissions("sys:user:role:update")
    public DataResult saveUserOwnRole(@RequestBody @Valid UserOwnRoleReqVO vo){
        DataResult result=DataResult.success();
        userService.setUserOwnRole(vo);
        return result;
    }


//    @GetMapping("/user/token")
//    @ApiOperation(value = "jwt token 刷新接口")
//    public DataResult<String> refreshToken(HttpServletRequest request){
//        String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
//        String newAccessToken = userService.refreshToken(refreshToken);
//        DataResult result=DataResult.success();
//        result.setData(newAccessToken);
//        return result;
//    }
    @GetMapping("/user/token")
    @ApiOperation(value = "jwt token 刷新接口")
    public DataResult<String> refreshToken(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String newAccessToken = userService.refreshToken(accessToken);
        DataResult result=DataResult.success();
        result.setData(newAccessToken);
        return result;
    }

    @PutMapping("/user/updateUser")
    @ApiOperation(value ="列表修改用户信息接口")
   // @RequiresPermissions("sys:user:update")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo, HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String userId= JwtTokenUtil.getUserId(accessToken);
        String vd = JWTHelper.vd(accessToken);
        DataResult result=DataResult.success();
        userService.updateUserInfo(vo,vd);
        return result;
    }

    @DeleteMapping("/user")
    @ApiOperation(value = "批量/删除用户接口")
   // @RequiresPermissions("sys:user:delete")
    public DataResult deletedUsers(@RequestBody @ApiParam(value = "用户id集合") List<String> list, HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String operationId=JwtTokenUtil.getUserId(accessToken);
        String vd = JWTHelper.vd(accessToken);
        userService.deletedUsers(list,vd);
        DataResult result=DataResult.success();
        return result;
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "用户信息详情接口")
    public DataResult<SysUser> detailInfo(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String id=JwtTokenUtil.getUserId(accessToken);
        String vd = JWTHelper.vd(accessToken);
        DataResult result=DataResult.success();
        result.setData(userService.detailInfo(vd));
        return result;
    }

    @PutMapping("/user/saveInfo")
    @ApiOperation(value = "保存个人信息接口")
    public DataResult saveUserInfo(@RequestBody UserUpdateDetailInfoReqVO vo, HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String id=JwtTokenUtil.getUserId(accessToken);
        String vd = JWTHelper.vd(accessToken);
        userService.userUpdateDetailInfo(vo,vd);
        DataResult result=DataResult.success();
        return result;
    }

    @PutMapping("/user/pwd")
    @ApiOperation(value = "修改个人密码接口")
    public DataResult updatePwd(@RequestBody @Valid UserUpdatePwdReqVO vo, HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String refresgToken=request.getHeader(Constant.REFRESH_TOKEN);
        userService.userUpdatePwd(vo,accessToken,refresgToken);
        DataResult result=DataResult.success();
        return result;
    }

    @GetMapping("/user/logout")
    @ApiOperation(value = "用户退出登录接口")
    public DataResult logout(HttpServletRequest request){
        try {
            String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
            String refreshToken=request.getHeader(Constant.REFRESH_TOKEN);
            userService.logout(accessToken,refreshToken);
        } catch (Exception e) {
            log.error("logout:{}",e);
        }
        return DataResult.success();
    }

    @PutMapping("/user/updateInfo")
    @ApiOperation(value = "保存个人信息接口")
    public DataResult updateUserInfo(@RequestBody UserUpdateInfoReqVO vo, HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String refresgToken=request.getHeader(Constant.REFRESH_TOKEN);
        String vd = JWTHelper.vd(accessToken);
        String id=JwtTokenUtil.getUserId(accessToken);
        userService.userUpdatePwdssp(vo,accessToken,refresgToken);
        userService.userUpdateDetailInfossp(vo,vd);
        DataResult result=DataResult.success();
        return result;
    }

}
