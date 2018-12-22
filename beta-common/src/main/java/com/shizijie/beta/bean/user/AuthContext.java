package com.shizijie.beta.bean.user;

import com.shizijie.beta.bean.user.dto.UserDTO;

/**
 * @author shizijie
 * @version 2018-12-15 下午7:30
 */
public class AuthContext {
    private static ThreadLocal<UserDTO> threadLocal=new ThreadLocal<>();

    public static void setUserInfo(UserDTO userDTO){
        threadLocal.set(userDTO);
    }
    public static UserDTO getUserInfo(){
        return threadLocal.get();
    }
    public static void clear(){
        threadLocal.remove();
    }
    public static String getUserId(){
        UserDTO userDTO=getUserInfo();
        if(userDTO!=null){
            return userDTO.getUserId();
        }
        return null;
    }
}
