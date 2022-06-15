package com.mk.ad.service.impl;

import com.mk.ad.entity.AdminSspAccount;
import com.mk.ad.entity.SysUser;
import com.mk.ad.mapper.SysUserMapper;
import com.mk.ad.mapper.mymapper.MyAdminSspAccountMapper;
import com.mk.ad.service.AdminSspAccountService;
import com.mk.ad.vo.resp.AccountRespVO;
import com.mk.ad.vo.resp.FinanceRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: AdminSspAccountServiceImpl
 * 账户信息
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Service
public class AdminSspAccountServiceImpl implements AdminSspAccountService {

    @Autowired
    private MyAdminSspAccountMapper myAdminSspAccountMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    /**
     *  根据userid查询账户信息
     * @param user_id
     * @return
     */
    @Override
    public AccountRespVO selectAccountByUserId(String user_id) {
        SysUser sysUser = sysUserMapper.selectByPrimaryKey(user_id);
        List<AdminSspAccount> asList = myAdminSspAccountMapper.selectAccountByUserId(user_id);
        AccountRespVO accountRespVO = new AccountRespVO();
        if(0 == sysUser.getSysType()){
            accountRespVO.setCompany_name(asList.get(0).getCompany_name());
            accountRespVO.setLicense_number(asList.get(0).getLicense_number());
            accountRespVO.setOrganization_code(asList.get(0).getOrganization_code());
            accountRespVO.setShareholder(asList.get(0).getShareholder());
            accountRespVO.setId(asList.get(0).getId().toString());
            accountRespVO.setStatus(0);
            return accountRespVO;
        }
        accountRespVO.setCompany_name(asList.get(0).getCompany_name());
        accountRespVO.setLicense_number(asList.get(0).getLicense_number());
        accountRespVO.setOrganization_code(asList.get(0).getOrganization_code());
        accountRespVO.setShareholder(asList.get(0).getShareholder());
        accountRespVO.setId(asList.get(0).getId().toString());
        Integer ss = asList.get(0).getStatus();
        accountRespVO.setStatus(ss);
        return accountRespVO;
    }

    /**
     *  根据userid查询财务信息
     * @param user_id
     * @return
     */
    @Override
    public FinanceRespVO selectFinanceByUserId(String user_id) {
        FinanceRespVO financeRespVO = new FinanceRespVO();

        List<AdminSspAccount> as =  myAdminSspAccountMapper.selectAccountByUserId(user_id);
        financeRespVO.setPayee(as.get(0).getPayee());
        financeRespVO.setBank_account(as.get(0).getBank_account());
        financeRespVO.setBank_deposit(as.get(0).getBank_deposit());

        return financeRespVO;
    }

}
