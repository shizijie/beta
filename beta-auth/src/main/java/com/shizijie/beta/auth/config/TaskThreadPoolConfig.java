package com.shizijie.beta.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class TaskThreadPoolConfig {
    private static int maxSize=Runtime.getRuntime().availableProcessors();
    @Bean("taskThreadPool")
    public Executor taskThreadPool(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        //核心线程数
        executor.setCorePoolSize(maxSize);
        //最大线程数
        executor.setMaxPoolSize(maxSize*2);
        //缓冲队列
        executor.setQueueCapacity(200);
        //允许线程空闲时间
        executor.setKeepAliveSeconds(60);
        //线程名前缀
        executor.setThreadNamePrefix("beta-Thread-");
        //拒绝任务处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
