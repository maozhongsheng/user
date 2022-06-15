package com.mk.ad.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: LoginRespVO
 * ssp系统账户返回值
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Data
public class AccountRespVO {
    @ApiModelProperty(value = "账户名称 (公司名称)")
    private String company_name;
    @ApiModelProperty(value = "营业执照注册号")
    private String license_number;
    @ApiModelProperty(value = "组织机构代码")
    private String organization_code;
    @ApiModelProperty(value = "股东姓名")
    private String shareholder;
    @ApiModelProperty(value = "账户ID")
    private String id;
    @ApiModelProperty(value = "账户状态")
    private Integer status;
}
