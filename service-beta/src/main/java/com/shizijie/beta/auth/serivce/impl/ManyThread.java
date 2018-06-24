package com.shizijie.beta.auth.serivce.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author shizijie
 * @version 2018-06-11 下午10:13
 */
public class ManyThread {
    public static void main(String[] args) {
        ExecutorService pool= Executors.newCachedThreadPool();
        pool.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
