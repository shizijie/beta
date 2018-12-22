package com.shizijie.beta.aspect;

import com.shizijie.beta.annotation.ThreadHelp;
import com.shizijie.beta.bean.thread.ThreadBean;
import com.shizijie.beta.utils.thread.ThreadPoolUtils;
import com.shizijie.beta.utils.thread.model.ThreadHelper;
import com.shizijie.beta.utils.thread.model.ThreadStatus;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Aspect
@Component
@Order(2)
public class ThreadAspect{

    @Pointcut("@annotation(com.shizijie.beta.annotation.ThreadHelp)")
    public void pointcut(){}

    private Map<String,ThreadStatus> map=new HashMap<>();
    private static ThreadPoolExecutor pool=ThreadPoolUtils.getThreadPool("test",4);

    @Around("pointcut()&&@annotation(threadHelp)")
    public Object lock(ProceedingJoinPoint joinPoint, ThreadHelp threadHelp) throws Throwable {
        Object result=null;
        if(map.get(threadHelp.value())==null){
            synchronized (map){
                if(map.get(threadHelp.value())==null){
                    map.put(threadHelp.value(), ThreadPoolUtils.getThreadStatus(threadHelp.value(),threadHelp.maxSize()));
                }
            }
        }
        try{
            Future future=pool.submit(new ThreadBean(map.get(threadHelp.value()),joinPoint));
            result=future;
        }catch (Throwable e){
            throw e;
        }finally {

        }
        return result;
    }

}
