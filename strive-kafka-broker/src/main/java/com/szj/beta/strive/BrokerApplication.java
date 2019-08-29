package com.szj.beta.strive;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shizijie
 * @version 2019-08-29 下午7:43
 */
@SpringBootApplication(scanBasePackages = "com.szj.beta")
public class BrokerApplication {
    public static void main(String[] args) {
        SpringApplication app=new SpringApplication(BrokerApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
