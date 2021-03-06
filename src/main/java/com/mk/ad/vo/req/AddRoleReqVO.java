package com.mk.ad.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName: AddRoleReqVO
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Data
public class AddRoleReqVO {

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态(1:正常0:弃用)")
    private Integer status;

    @ApiModelProperty(value = "拥有的权限id集合")
    private List<String> permissions;

    @ApiModelProperty(value = "拥有的菜单权限id集合")
    private List  role;
}
