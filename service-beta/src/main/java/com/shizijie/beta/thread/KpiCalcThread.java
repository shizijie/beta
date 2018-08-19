package com.shizijie.beta.thread;

/**
 * @author shizijie
 * @version 2018-08-19 下午10:28
 */
public class KpiCalcThread implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("1");
        System.out.println(1/0);

    }

}
