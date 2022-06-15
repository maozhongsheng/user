package com.mk.ad.vo.resp;

import com.mk.ad.entity.SysPermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: LoginRespVO
 * ssp系统账户返回值
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Data
public class RoleRespVO {

    @ApiModelProperty(value = "角色名称")
    private String name;
    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态(1:正常0:弃用)")
    private Integer status;

    @ApiModelProperty(value = "拥有的权限id集合")
    private List<String> permissions;
    @ApiModelProperty(value = "拥有的权限name集合")
    private List<String> permissionsName;

    @ApiModelProperty(value = "拥有的权限idandname集合")
    private List<SysPermission> permissionsTous;
}
