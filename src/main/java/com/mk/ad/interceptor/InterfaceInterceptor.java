package com.mk.ad.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mk.ad.utils.JWTHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class InterfaceInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(InterfaceInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            return true;
        }
        //获取请求头中的token
        String authtoken = request.getHeader("authorization");
        PrintWriter writer = null;
        JSONObject jsonObject = new JSONObject();
        try {
            //判断请求头中的authtoken是否有值及是否验证通过
            if (!StringUtils.isBlank(authtoken)) {
                String vd = JWTHelper.vd(authtoken);
                if (null == vd) {
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=utf-8");
                    response.setHeader("Access-Control-Allow-Credentials","true");
                    response.setHeader("Access-Control-Allow-Origin", "*");
                    writer = response.getWriter();
                    jsonObject.put("code", 4010002);
                    jsonObject.put("message", "token验签失败或过期，请重新登录");
                    writer.append(jsonObject.toString());
                } else {
                    flag = true;
                    String queryString = request.getQueryString();
                    logger.info("拦截器中queryString=================" + queryString);
                    request.setAttribute("authToken", vd);
                }
            } else {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=utf-8");
                response.setHeader("Access-Control-Allow-Credentials","true");
                response.setHeader("Access-Control-Allow-Origin", "*");
                writer = response.getWriter();
                jsonObject.put("code", 8);
                jsonObject.put("message", "非法请求未接收到token");
                writer.append(jsonObject.toString());
            }
        } catch (IOException e) {
            logger.error("拦截器出现异常：", e);
        } finally {
            if (null != writer) {
                writer.flush();
                writer.close();
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
