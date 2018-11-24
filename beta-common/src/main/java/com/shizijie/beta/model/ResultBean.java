package com.shizijie.beta.model;

import com.shizijie.beta.params.ResultParams;
import lombok.Data;

/**
 * @author shizijie
 * @version 2018-06-10 下午8:16
 */
@Data
public class ResultBean<T> {
    /**返回编码*/
    private String code;
    /**编码信息*/
    private String msg;
    /**返回信息结果*/
    private T result;

    public static <T>ResultBean success(T obj){
        ResultBean bean=new ResultBean();
        bean.setCode(ResultParams.CODE_200.getCode());
        bean.setMsg(ResultParams.CODE_200.getMsg());
        bean.setResult(obj);
        return bean;
    }
    public static <T>ResultBean success(){
        ResultBean bean=new ResultBean();
        bean.setCode(ResultParams.CODE_200.getCode());
        bean.setMsg(ResultParams.CODE_200.getMsg());
        return bean;
    }
    public static <T>ResultBean fail(String msg){
        ResultBean bean=new ResultBean();
        bean.setCode(ResultParams.CODE_500.getCode());
        bean.setMsg(msg);
        return bean;
    }
    public static <T>ResultBean fail500(){
        ResultBean bean=new ResultBean();
        bean.setCode(ResultParams.CODE_500.getCode());
        bean.setMsg(ResultParams.CODE_500.getMsg());
        return bean;
    }
    public static <T>ResultBean fail403(){
        ResultBean bean=new ResultBean();
        bean.setCode(ResultParams.CODE_403.getCode());
        bean.setMsg(ResultParams.CODE_403.getMsg());
        return bean;
    }
}
