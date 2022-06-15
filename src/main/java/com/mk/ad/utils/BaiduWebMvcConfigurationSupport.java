package com.mk.ad.utils;

import com.mk.ad.interceptor.InterfaceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class BaiduWebMvcConfigurationSupport implements WebMvcConfigurer {
//,"/api/user/saveInfo"
private String [] whitelist = {"/api/user/login","/api/userIdByToken","/api/selectUpperBySlotId","/api/adminIndexSlotId","/api/adminSelectMediaName","/api/adminSelectSlotName","/api/adminSelectSspCompanyName","/api/adminSelectSlotTypeName","/api/adminUnionSlotId","/api/adminSelectUnionName","/api/adminSelectUnionMediaName","/api/adminSelectUnionSlotName","/api/adminIndexUnionMedia"};


    @Bean
    public BaiduHandlerInterceptor baiduHandlerInterceptor() {
        return new BaiduHandlerInterceptor();
    }

    @Bean
    public InterfaceInterceptor interfaceInterceptor() {
        return new InterfaceInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(baiduHandlerInterceptor()).addPathPatterns("/detail/*", "/search/*", "/info/*", "/sitemap.xml", "/sitemap.html");

        InterceptorRegistration interceptorRegistration = registry.addInterceptor(interfaceInterceptor());
        //设置白名单路径
        interceptorRegistration.excludePathPatterns(whitelist);
        //设置拦截路径
        interceptorRegistration.addPathPatterns("/**");

        //super.addInterceptors(registry);
    }

    //    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(baiduHandlerInterceptor()).addPathPatterns("/detail/*","/search/*","/info/*","/sitemap.xml","/sitemap.html").excludePathPatterns("/**");
//        super.addInterceptors(registry);
//    }

}
