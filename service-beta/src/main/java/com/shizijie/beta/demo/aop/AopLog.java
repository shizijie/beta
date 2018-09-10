package com.shizijie.beta.demo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AopLog {
    @Pointcut("@annotation(com.shizijie.beta.demo.aop.AopSign)")
    public void pointcut(){}

    @Pointcut("execution(public * com.shizijie.beta.demo..*.*(..))")
    public void printLog(){}

    @Around("pointcut()&&@annotation(aopSign)")
    public void test(ProceedingJoinPoint proceedingJoinPoint,AopSign aopSign){
        try{
            proceedingJoinPoint.proceed();
        }catch (Throwable e){

        }
    }

    @Around("printLog()")
    public void test2(ProceedingJoinPoint proceedingJoinPoint){
        try{
            proceedingJoinPoint.proceed();
        }catch (Throwable e){

        }
    }

}
