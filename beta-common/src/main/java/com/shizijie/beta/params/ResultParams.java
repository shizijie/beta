package com.shizijie.beta.params;

/**
 * @author shizijie
 * @version 2018-11-24 上午10:18
 */
public enum  ResultParams {

    CODE_200("200","操作成功！"),

    CODE_403("403","暂无权限！"),

    CODE_500("500","系统正忙！");

    private String code;

    private String msg;

    ResultParams(String code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public String getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }


}
