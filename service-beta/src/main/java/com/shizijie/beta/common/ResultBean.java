package com.shizijie.beta.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shizijie
 * @version 2018-06-10 下午8:16
 */
@Data
public class ResultBean {
    /**返回编码*/
    private String code;
    /**编码信息*/
    private String codeInfo;
    /**返回信息结果*/
    private Object result;

    public static ResultBean success(Object obj){
        ResultBean bean=new ResultBean();
        bean.setCode("00000");
        bean.setResult(obj);
        return bean;
    }
    public static ResultBean fail(String errorInfo){
        ResultBean bean=new ResultBean();
        bean.setCode("99999");
        bean.setCodeInfo(errorInfo);
        return bean;
    }
}
