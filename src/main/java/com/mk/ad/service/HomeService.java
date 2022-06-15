package com.mk.ad.service;


import com.mk.ad.vo.resp.HomeRespVO;

/**
 * @ClassName: HomeService
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
public interface HomeService {
    HomeRespVO getHome(String userId);
}
