package com.mk.ad;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * Created by mzs
 */
@EnableFeignClients
@EnableEurekaClient
//@EnableHystrix
//@EnableCircuitBreaker
//@EnableDiscoveryClient
//@EnableHystrixDashboard
@MapperScan("com.mk.ad.mapper")
@SpringBootApplication
public class MkUserApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MkUserApplication.class,args);
    }

}
