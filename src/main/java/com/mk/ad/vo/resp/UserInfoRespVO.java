package com.mk.ad.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: UserInfoRespVO
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Data
public class UserInfoRespVO {
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "部门id")
    private String deptId;
    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

    @ApiModelProperty(value = "用户名称")
    private String username;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "账户角色")
    private String rolename;
    @ApiModelProperty(value = "账户角色id")
    private String roleid;
    @ApiModelProperty(value = "菜单权限id")
    private String jurisdictionId;

}
