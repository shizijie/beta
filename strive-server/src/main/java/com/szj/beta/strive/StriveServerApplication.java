package com.szj.beta.strive;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shizijie
 * @version 2019-07-24 下午11:41
 */
@SpringBootApplication(scanBasePackages = "com.szj.beta")
//@EnableEncry
public class StriveServerApplication {
    public static void main(String[] args) {
        SpringApplication app=new SpringApplication(StriveServerApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
