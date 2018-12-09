package com.shizijie.beta.utils.id;

import com.shizijie.beta.bean.id.IdWorker;
import com.shizijie.beta.utils.spring.SpringContextUtil;

/**
 * @author shizijie
 * @version 2018-12-09 下午4:18
 */
public class SnowFlakeUtils {
    private static String BEAN_NAME="idWorker";

    public static long nextId(){
        IdWorker idWorker= SpringContextUtil.getBean(BEAN_NAME);
        return idWorker.nextId();
    }
}
