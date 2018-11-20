package com.shizijie.beta.utils.string;
/**
 * @author shizijie
 * @version 2018-06-10 下午10:12
 */
public class StringUtils3 {
    /**
     * 校验是否为数字
     * @param str
     * @return
     */
    public static boolean isNumber(String str){
        String reg="-[0-9]+(.[0-9]+)?|[0-9]+(.[0-9]+)?";
        return str==null?false:str.matches(reg);
    }
}
