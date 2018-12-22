package com.shizijie.beta.utils.thread.model;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListMap;

@Slf4j
public abstract class ThreadHelper<T> implements Callable{
    protected ThreadStatus threadStatus;
    private long start;
    private int serialNum;

    public void begin() {
        Objects.requireNonNull(threadStatus);
        serialNum=threadStatus.serialNumAdd();
        if(threadStatus.isPrintLog()){
            log.info("[ {} ]  begin-[ {} ] ，remain-[ {} ] , size-[ {} ]",
                    threadStatus.getThreadName(),serialNum,threadStatus.getRemainNum(),threadStatus.getTotalNum());
        }
        start=System.currentTimeMillis();
    }
    public void end(){
        threadStatus.remainNumSub();
        if(threadStatus.isPrintLog()){
            log.info("[ {} ]  end-[ {} ] ， time-[ {} ] , remain-[ {} ] , size-[ {} ]",
                    threadStatus.getThreadName(),serialNum,System.currentTimeMillis()-start,threadStatus.getRemainNum(),threadStatus.getTotalNum());
        }
    }
    @Override
    public T call() throws Exception {
        try {
            begin();
            T object=doSomething();
            return object;
        } finally {
            end();
        }
    }
    public abstract T doSomething();

}
