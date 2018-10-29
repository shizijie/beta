package com.shizijie.beta.demo.thread;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutorService;

@Repository
public class ThreadTest implements InitializingBean {

    private ExecutorService executor;
    @Override
    public void afterPropertiesSet() throws Exception {
        //init执行之前执行

    }
}
