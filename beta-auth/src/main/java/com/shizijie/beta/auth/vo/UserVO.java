package com.shizijie.beta.auth.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author shizijie
 * @version 2018-06-10 下午7:13
 */
@Data
public class UserVO {
    @NotNull(message = "userName is not null")
    /**用户名称*/
    private String userName;
    @NotNull(message = "password is not null")
    /**密码*/
    private String password;
}
