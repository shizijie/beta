package com.shizijie.beta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @author shizijie
 * @version 2018-06-10 下午10:12
 */
@SpringBootApplication
/**注册中心为eureka*/
@EnableEurekaClient
/**通过feign调用eureka服务*/
@EnableFeignClients
/**实时监控Hystrix的各项指标信息*/
@EnableHystrixDashboard
/**开启断路器*/
@EnableHystrix
@MapperScan("com.shizijie.beta.*")
@EnableScheduling
@EnableTransactionManagement
public class BetaAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(BetaAuthApplication.class,args);
    }
}