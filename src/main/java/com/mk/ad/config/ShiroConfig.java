package com.mk.ad.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.mk.ad.shiro.CustomAccessControlFilter;
import com.mk.ad.shiro.CustomHashedCredentialsMatcher;
import com.mk.ad.shiro.CustomRealm;
import com.mk.ad.shiro.ShiroCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfig
 * TODO:类文件简单描述
 * @Author: yjn
 * @UpdateUser: yjn
 * @Version: 0.0.1
 */
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroCacheManager cacheManager(){
        return new ShiroCacheManager();
    }

    @Bean
    public CustomHashedCredentialsMatcher customHashedCredentialsMatcher(){
        return new CustomHashedCredentialsMatcher();
    }
    @Bean
    public CustomRealm customRealm(){
        CustomRealm customRealm=new CustomRealm();
        customRealm.setCredentialsMatcher(customHashedCredentialsMatcher());
        customRealm.setCacheManager(cacheManager());
        return customRealm;
    }
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(customRealm());
        return defaultWebSecurityManager;
    }
    /**
     * shiro过滤器，配置拦截哪些请求
     * @Author:      yjn
     * @UpdateUser:
     * @Version:     0.0.1
     * @param securityManager
     * @return       org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @throws
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //自定义拦截器限制并发人数,参考博客：
        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //用来校验token
        filtersMap.put("token", new CustomAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/**", "anon");//登录
       //filterChainDefinitionMap.put("http://192.168.11.74:8009/api/user/login", "anon");//登录
//        filterChainDefinitionMap.put("/api/user/roles", "authc");//登录
//        filterChainDefinitionMap.put("/api/logs", "authc");//登录
//        filterChainDefinitionMap.put("/api/permission", "authc");//登录

//        filterChainDefinitionMap.put("http://8.140.99.234:8009/api/account/{user_id}", "anon");//账户信息
//        filterChainDefinitionMap.put("http://8.140.99.234:8009/api/user/info", "anon");//账户信息保存
//        filterChainDefinitionMap.put("http://8.140.99.234:8009/index/**","anon");
//        filterChainDefinitionMap.put("http://8.140.99.234:8009/api/user/updateInfo","anon");
//        filterChainDefinitionMap.put("http://192.168.11.74:8009/api/users","anon");
//        filterChainDefinitionMap.put("http://127.0.0.1:8009/api/user/login", "anon");//登录
//        filterChainDefinitionMap.put("http://192.168.11.243:8009/api/account/{user_id}", "anon");//账户信息
//        filterChainDefinitionMap.put("http://192.168.11.243:8009/api/user/info", "anon");//账户信息保存
        filterChainDefinitionMap.put("/index/**","anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/treetable-lay/**", "anon");
        filterChainDefinitionMap.put("/api/user/token", "anon");
        //放开swagger-ui地址
        filterChainDefinitionMap.put("/swagger/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/captcha.jpg", "anon");
        //druid sql监控配置
        filterChainDefinitionMap.put("/druid/**", "anon");
       // filterChainDefinitionMap.put("/**","token,authc");
       // filterChainDefinitionMap.put("http://8.140.99.234:8009/**","token,authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     * @Author:      yjn
     * @UpdateUser:
     * @Version:     0.0.1
     * @param securityManager
     * @return       org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @throws
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

}
