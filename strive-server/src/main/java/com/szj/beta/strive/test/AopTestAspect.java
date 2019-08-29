package com.szj.beta.strive.test;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author shizijie
 * @version 2019-08-06 下午11:24
 */
@Aspect
@Component
public class AopTestAspect {
    @Pointcut("@annotation(com.szj.beta.strive.test.AopTest)")
    public void pointcut() {

    }

    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }

}
