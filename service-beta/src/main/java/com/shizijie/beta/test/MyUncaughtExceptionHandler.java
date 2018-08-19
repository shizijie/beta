package com.shizijie.beta.test;

/**
 * @author shizijie
 * @version 2018-08-19 下午11:22
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Catched Throwable: " + e.getClass().getSimpleName() + ", " + e.getMessage());
    }
}
