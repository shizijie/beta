package com.shizijie.beta.auth.demo.threadlocal;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.util.StopWatch;

@Slf4j
public class StopWatchHander {
    private static final ThreadLocal<StopWatch> threadLocal=new ThreadLocal<>();

    public static void initStopWatch(String stopWatchName){
        StopWatch stopWatch=new StopWatch(stopWatchName);
        stopWatch.start();
        threadLocal.set(stopWatch);
    }

    public static void log(Logger logger,long exTime,String msg){
        StopWatch stopWatch=threadLocal.get();
        if(stopWatch==null){
            return;
        }
        if(stopWatch.isRunning()){
            stopWatch.stop();
            if(stopWatch.getLastTaskTimeMillis()>=exTime){
                logger.info(msg+"-{}ms",stopWatch.getLastTaskTimeMillis());
            }
        }
        stopWatch.start();
    }

    public static void stop(){
        StopWatch stopWatch=threadLocal.get();
        if(stopWatch==null){
            return;
        }
        stopWatch.stop();
    }

    public static void main(String[] args) throws InterruptedException {
        StopWatchHander.initStopWatch("test");
        Thread.sleep(1000);
        StopWatchHander.log(log,100,"sleep");
        StopWatchHander.stop();
    }
}
