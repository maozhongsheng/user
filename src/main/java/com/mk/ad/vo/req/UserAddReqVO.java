package com.mk.ad.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName: UserAddReqVO
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Data
public class UserAddReqVO {

    @ApiModelProperty(value = "账号")
    @NotBlank(message = "账号不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "所属角色")
    @NotBlank(message = "所属角色不能为空")
    private String roleId;

//    @ApiModelProperty(value = "所属部门")
//    @NotBlank(message = "所属部门不能为空")
//    private String deptId;

    @ApiModelProperty(value = "创建来源(1.web 2.android 3.ios )")
    private String createWhere;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定 )")
    private Integer status;

    @ApiModelProperty(value = "分配的菜单权限")
    private List role;

    @ApiModelProperty(value = "登录类型 1：ssp;2：admin;3:：dsp")
   // @NotBlank(message = "用户登录类型不能为空")
    private String sysType;
}
