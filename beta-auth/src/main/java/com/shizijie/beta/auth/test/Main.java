package com.shizijie.beta.auth.test;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Configuration
@Slf4j
public class Main implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        for(int i=0;i<100;i++){
//            final int num=i;
//            taskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));
//            taskRegistrar.addTriggerTask(()->{
//                System.out.println(Thread.currentThread().getName()+"------------------开始"+num);
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(Thread.currentThread().getName()+"------------------结束"+num);
//            },triggerContext -> {return new CronTrigger("0 * * * * ?").nextExecutionTime(triggerContext);
//            });
//        }
    }
}
