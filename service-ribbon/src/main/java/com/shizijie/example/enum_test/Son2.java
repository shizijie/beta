package com.shizijie.example.enum_test;

/**
 * @author shizijie
 * @version 2018-07-21 上午8:29
 */
public class Son2 implements Father {
    @Override
    public void say(String str) {
        System.out.println("son2:"+str);
    }
}
