package com.mk.ad.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: LoginRespVO
 *  ssp系统财务信息返回值
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Data
public class FinanceRespVO {

    @ApiModelProperty(value = "收款方户名")
    private String payee;

    @ApiModelProperty(value = "银行账号")
    private String bank_account;

    @ApiModelProperty(value = "开户行")
    private String bank_deposit;

}
