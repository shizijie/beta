package com.szj.beta.strive.test;

import org.springframework.stereotype.Component;

/**
 * @author shizijie
 * @version 2019-08-06 下午11:23
 */
@Component
public class TestService {
    @AopTest
    public void test(){
        System.out.println("123");
    }

}
