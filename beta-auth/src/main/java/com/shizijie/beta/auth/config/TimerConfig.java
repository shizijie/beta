package com.shizijie.beta.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimerConfig {


    //@Scheduled(cron = "0/5 * * * * ?")
    void test(){
        System.out.println("----跑批开始----");
        //redisUtils.set("22","xl");

        System.out.println("-----跑批结束-------");
    }


}
