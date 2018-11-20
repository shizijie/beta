package com.shizijie.beta.test;


import com.shizijie.beta.utils.encoding.EncodingDetect;

/**
 * @author shizijie
 * @version 2018-08-17 下午11:56
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        System.out.println("2018-31".compareTo("2018-32"));
        //ExecutorService pool= Executors.newFixedThreadPool(3);
        String code= EncodingDetect.getJavaEncode("E:\\xl\\1.txt");
        System.out.println(code);
    }
}
