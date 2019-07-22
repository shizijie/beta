package com.shizijie.beta.auth.test;

import java.lang.reflect.Field;

/**
 * @author shizijie
 * @version 2019-04-11 下午10:20
 */
public class Value {
    public static void main(String[] args) throws Exception{
        Integer a=1;
        Integer b=2;
        //System.out.println("a="+a+",b="+b);
        swap(a,b);
        Integer c=1;
        System.out.println("c="+c);

        //System.out.println("a="+a+",b="+b);

    }

    private static void swap(Integer a, Integer b) throws Exception {
        int temp=a;
        Class<? extends Integer> clazz=a.getClass();
        Field avalue=clazz.getDeclaredField("value");
        avalue.setAccessible(true);
        avalue.set(a,b);
        Class<? extends Integer> clazz2=b.getClass();
        Field bvalue=clazz2.getDeclaredField("value");
        bvalue.setAccessible(true);
        bvalue.set(b,new Integer(temp));
    }
}
