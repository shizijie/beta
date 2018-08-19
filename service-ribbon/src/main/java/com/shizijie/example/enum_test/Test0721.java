package com.shizijie.example.enum_test;


import org.springframework.util.StringUtils;

/**
 * @author shizijie
 * @version 2018-07-21 上午8:03
 */
public enum Test0721 {
    KPI01("1"),KPI02("2");
    private String value;
    Test0721(String value){
        this.value=value;
    }

    public static void main(String[] args) {
        System.out.println(Test0721.KPI01.value);
        System.out.println(Test0721.KPI01);
        Test0721 test = Test0721.valueOf("KPI02");
        System.out.println(test.value);
        System.out.println(Father.Example.SON1);
        Father.Example.SON1.say("TEST");
        String str=null;
        System.out.println(StringUtils.hasText(str));
    }
}

