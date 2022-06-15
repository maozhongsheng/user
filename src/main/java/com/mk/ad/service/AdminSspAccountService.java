package com.mk.ad.service;

import com.mk.ad.vo.resp.AccountRespVO;
import com.mk.ad.vo.resp.FinanceRespVO;

/**
 * @ClassName: AccountService
 * TODO: 账户信息
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
public interface AdminSspAccountService {

    AccountRespVO selectAccountByUserId(String user_id);

    FinanceRespVO selectFinanceByUserId(String user_id);

}
