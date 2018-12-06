package com.shizijie.beta.auth.demo.enums;

public enum EnumTest {
    SUCCESS("000","SUCCESS"),
    ERROR("999","ERROR");
    private String code;
    private String info;
    EnumTest(String str1, String str2){
        this.code=str1;
        this.info=str2;
    }

    public String getCode(){
        return code;
    }

    public String getInfo(){
        return info;
    }

}
