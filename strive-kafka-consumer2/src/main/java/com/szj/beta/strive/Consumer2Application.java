package com.szj.beta.strive;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shizijie
 * @version 2019-08-29 下午7:42
 */
@SpringBootApplication(scanBasePackages = "com.szj.beta")
public class Consumer2Application {
    public static void main(String[] args) {
        SpringApplication app=new SpringApplication(Consumer2Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
