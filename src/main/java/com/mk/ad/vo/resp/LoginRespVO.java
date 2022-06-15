package com.mk.ad.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: LoginRespVO
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Data
public class LoginRespVO {
    @ApiModelProperty(value = "正常的业务token")
    private String accessToken;
    @ApiModelProperty(value = "刷新token")
    private String refreshToken;
    @ApiModelProperty(value = "用户id")
    private String id;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "联系人")
    private String realName;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "权限名称")
    private String rolename;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "所有权限id")
    private String jurisdiction;
    @ApiModelProperty(value = "DSP权限分类 0:管理员;1:代理;2:直客")
    private Integer dspType;
    @ApiModelProperty(value = "公司简称")
    private String company_profile;
    @ApiModelProperty(value = "0:不可修改  1:可修改")
    private Integer is_up;
    @ApiModelProperty(value = "dspId")
    private String dsp_id;
}
