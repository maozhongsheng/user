package com.mk.ad.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName: LoginReqVO
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Data
public class LoginReqVO {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value ="密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "登录类型 1：pc；2：App")
    @NotBlank(message = "用户登录类型不能为空")
    private String type;

    @ApiModelProperty(value = "登录类型 1：admin;2：dsp;3:：ssp")
    @NotBlank(message = "用户登录类型不能为空")
    private String sysType;
}
