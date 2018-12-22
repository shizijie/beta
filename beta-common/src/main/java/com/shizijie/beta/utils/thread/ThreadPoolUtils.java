package com.shizijie.beta.utils.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.shizijie.beta.utils.thread.model.ThreadPoolException;
import com.shizijie.beta.utils.thread.model.ThreadStatus;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolUtils {
    public static int maxSize=Runtime.getRuntime().availableProcessors()*2;

    public static ThreadPoolExecutor getThreadPool(String threadName,int maxSize){
        ThreadFactory factory=new ThreadFactoryBuilder().setNameFormat(threadName.concat("-%d"))
                .setUncaughtExceptionHandler(new ThreadPoolException()).build();
        ThreadPoolExecutor pool=new ThreadPoolExecutor(maxSize,maxSize,1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1024),factory,new ThreadPoolExecutor.AbortPolicy());
        pool.allowCoreThreadTimeOut(true);
        return pool;
    }
    public static ThreadPoolExecutor getThreadPool(String threadName){
        return getThreadPool(threadName,maxSize);
    }

    public static ThreadStatus getThreadStatus(String threadName,int threadSize){
        return new ThreadStatus(threadName,threadSize);
    }
    @SneakyThrows
    public static <T> List<T> awaitResult(List list,int sleepMillis){
        List<T> result=new ArrayList<>();
        while (list.size()>0){
            Iterator iterator=list.iterator();
            while (iterator.hasNext()){
                Future<T> future= (Future<T>) iterator.next();
                if(future.isDone()&&!future.isCancelled()){
                    result.add(future.get());
                    iterator.remove();
                }else{
                    Thread.sleep(sleepMillis);
                }
            }
        }
        return result;
    }
}
