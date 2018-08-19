package com.shizijie.beta.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shizijie
 * @version 2018-08-17 下午11:56
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("2018-31".compareTo("2018-32"));
        ExecutorService pool= Executors.newFixedThreadPool(3);
    }
}
