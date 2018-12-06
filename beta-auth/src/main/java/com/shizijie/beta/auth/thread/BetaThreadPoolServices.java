package com.shizijie.beta.auth.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.shizijie.beta.auth.test.MyUncaughtExceptionHandler;

import java.util.concurrent.*;

/**
 * @author shizijie
 * @version 2018-08-22 下午9:26
 */
public class BetaThreadPoolServices {
    private static int maxSize=Runtime.getRuntime().availableProcessors();

    public static ThreadPoolExecutor getThreadPool(String name,int poolMaxSize){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(name.concat("-%d"))
                .setUncaughtExceptionHandler(new MyUncaughtExceptionHandler()).build();
        ThreadPoolExecutor pool= new ThreadPoolExecutor(poolMaxSize, poolMaxSize,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());
        pool.allowCoreThreadTimeOut(true);
        return pool;
    }

    public static ThreadPoolExecutor getThreadPool(String name){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat(name.concat("-%d"))
                .setUncaughtExceptionHandler(new MyUncaughtExceptionHandler()).build();
        ThreadPoolExecutor pool= new ThreadPoolExecutor(maxSize, maxSize,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());
        pool.allowCoreThreadTimeOut(true);
        return pool;
    }
}
