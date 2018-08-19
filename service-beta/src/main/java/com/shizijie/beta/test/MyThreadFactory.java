package com.shizijie.beta.test;

import java.util.concurrent.ThreadFactory;

/**
 * @author shizijie
 * @version 2018-08-19 下午11:25
 */
public class MyThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("Thread[" + t.getName() + "] created.");
        return t;
    }
}
