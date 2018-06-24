package com.shizijie.beta.auth.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    /**用户id*/
    private String userId;
    /**用户名称*/
    private String userName;
    /**密码*/
    private String password;
    /**是否有效*/
    private String isValid;
    /**权限List*/
    private List<String> authList;
}
