package com.shizijie.example.enum_test;

/**
 * @author shizijie
 * @version 2018-07-21 上午8:26
 */
public interface Father {
    void say(String str);
    enum Example implements Father{
        SON1 {
            @Override
            public void say(String str) {
                System.out.println("son1:"+str);
            }
        },
        SON2 {
            @Override
            public void say(String str) {
                System.out.println("son1:"+str);
            }
        }
    }

}
