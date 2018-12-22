package com.shizijie.beta.auth.test;


import com.shizijie.beta.utils.encoding.EncodingDetect;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author shizijie
 * @version 2018-08-17 下午11:56
 */
public class Test1 {
    public static void main(String[] args) throws Exception {
        System.out.println("2018-31".compareTo("2018-32"));
        Date date=Date.from(ZonedDateTime.now().plusMinutes(1).toInstant());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        System.out.println(ZonedDateTime.now().plusMinutes(1).toInstant());
    }
}
