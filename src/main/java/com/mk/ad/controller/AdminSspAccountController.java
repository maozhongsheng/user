package com.mk.ad.controller;

import com.mk.ad.service.AdminSspAccountService;
import com.mk.ad.utils.DataResult;
import com.mk.ad.vo.resp.AccountRespVO;
import com.mk.ad.vo.resp.FinanceRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: AccountController
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "组织管理-账户管理",description = "用户模块相关接口")
@Slf4j
@CrossOrigin(value = "*", maxAge = 3600, allowCredentials = "true")
public class AdminSspAccountController {

    @Autowired
    private AdminSspAccountService accountService;

    @GetMapping("/account/{user_id}")
    @ApiOperation(value = "根据用户id查询账户信息")
//    @RequiresPermissions("sys:account:detail")
    public DataResult<AccountRespVO> selectAccountByUserId(@PathVariable("user_id") String user_id){
        DataResult result=DataResult.success();
        result.setData(accountService.selectAccountByUserId(user_id));
        return result;
    }

    @GetMapping("/finance/{user_id}")
    @ApiOperation(value = "根据用户id查询财务信息")
//    @RequiresPermissions("sys:account:detail")
    public DataResult<FinanceRespVO> selectFinanceByUserId(@PathVariable("user_id") String user_id){
        DataResult result=DataResult.success();
        result.setData(accountService.selectFinanceByUserId(user_id));
        return result;
    }



}
