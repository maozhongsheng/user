package com.mk.ad.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: HomeRespVO
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Data
public class HomeRespVO {
    @ApiModelProperty(value = "用户信息")
    private UserInfoRespVO userInfoVO;

    @ApiModelProperty(value = "首页菜单导航数据")
    private List<PermissionRespNodeVO> menus;
}
