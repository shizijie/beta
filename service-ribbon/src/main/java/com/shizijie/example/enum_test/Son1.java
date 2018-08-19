package com.shizijie.example.enum_test;

/**
 * @author shizijie
 * @version 2018-07-21 上午8:28
 */
public class Son1 implements Father {
    @Override
    public void say(String str) {
        System.out.println("son1:"+str);
    }
}
