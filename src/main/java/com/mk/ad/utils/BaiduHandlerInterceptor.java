package com.mk.ad.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaiduHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BaiduHandlerInterceptor.class);

    private static final String SPIDER_NAME="Baiduspider/2.0";
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String ua = request.getHeader("user-agent");
        if(ua.indexOf(SPIDER_NAME)>-1){
            logger.info("code:{}--url:{}--UA:{}--",response.getStatus(),request.getRequestURI(),ua);
        }
    }
}
