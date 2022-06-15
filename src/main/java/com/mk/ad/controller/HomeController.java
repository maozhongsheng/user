package com.mk.ad.controller;

import com.mk.ad.constants.Constant;
import com.mk.ad.service.HomeService;
import com.mk.ad.utils.DataResult;
import com.mk.ad.utils.JwtTokenUtil;
import com.mk.ad.vo.resp.HomeRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: HomeController
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@RestController
@RequestMapping("/api")
@Api(tags = "首页模块",description = "首页相关模块")
@CrossOrigin(value = "*", maxAge = 3600, allowCredentials = "true")
public class HomeController {
    @Autowired
    private HomeService homeService;
    @GetMapping("/home")
    @ApiOperation(value = "获取首页数据接口")
    public DataResult<HomeRespVO> getHome(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String userId= JwtTokenUtil.getUserId(accessToken);
        DataResult result =DataResult.success();
        result.setData(homeService.getHome(userId));
        return result;
    }
}
