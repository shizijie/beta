package com.shizijie.beta.auth.web;

import com.shizijie.beta.utils.id.SnowFlakeUtils;

/**
 * @author shizijie
 * @version 2018-12-09 下午8:05
 */
public class Task implements Runnable {
    @Override
    public void run() {
        long start=System.currentTimeMillis();
        while (System.currentTimeMillis()-start<100){
            System.out.println(SnowFlakeUtils.nextId());
        }
    }
}
