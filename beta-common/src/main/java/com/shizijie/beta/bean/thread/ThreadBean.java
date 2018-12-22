package com.shizijie.beta.bean.thread;

import com.shizijie.beta.utils.thread.model.ThreadHelper;
import com.shizijie.beta.utils.thread.model.ThreadStatus;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;

public class ThreadBean extends ThreadHelper {

    private ProceedingJoinPoint joinPoint;

    public ThreadBean(ThreadStatus threadStatus,ProceedingJoinPoint joinPoint){
        super.threadStatus=threadStatus;
        this.joinPoint=joinPoint;
    }

    @Override
    @SneakyThrows
    public Object doSomething() {
        return joinPoint.proceed();
    }
}
