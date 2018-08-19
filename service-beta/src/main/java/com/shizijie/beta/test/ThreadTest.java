package com.shizijie.beta.test;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.shizijie.beta.thread.KpiCalcThread;
import com.sun.tools.corba.se.idl.constExpr.Positive;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;

/**
 * @author shizijie
 * @version 2018-08-19 下午10:36
 */
public class ThreadTest {
    public static void main(String[] args) {
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
//
//        ExecutorService pool1 = new ThreadPoolExecutor(5, 200,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        ScheduledExecutorService pool = new ScheduledThreadPoolExecutor(4,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        for(int i=0;i<100;i++){
            pool.execute(new KpiCalcThread());

        }
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
