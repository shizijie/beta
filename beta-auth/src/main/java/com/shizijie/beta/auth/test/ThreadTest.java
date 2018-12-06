package com.shizijie.beta.auth.test;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.shizijie.beta.auth.thread.KpiCalcThread;

import java.util.concurrent.*;

/**
 * @author shizijie
 * @version 2018-08-19 下午10:36
 */
public class ThreadTest {
    public static void main(String[] args) {
        //Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d")
                .setUncaughtExceptionHandler(new MyUncaughtExceptionHandler()).build();
        //MyThreadFactory t=new MyThreadFactory();
        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory,new ThreadPoolExecutor.AbortPolicy());

//        ScheduledExecutorService pool = new ScheduledThreadPoolExecutor(4,
//                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d")
//                        .uncaughtExceptionHandler(new MyUncaughtExceptionHandler()).daemon(true).build());
        for(int i=0;i<2;i++){
//            Future future=pool.submit(new KpiCalcThread(i));
//            try {
//                future.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                System.out.println("222222222222222");
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//                System.out.println("333333333333333");
//            }
            try {

                pool.execute(new KpiCalcThread(i));
            }catch (Exception e){
                System.out.println("main111111111111111111111");
            }
        }
        try {
            pool.shutdown();
            boolean isDone;
            // 等待线程池终止
            do {
                isDone = pool.awaitTermination(1, TimeUnit.DAYS);
                System.out.println("awaitTermination...");
            } while(!isDone);
            System.out.println("end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
