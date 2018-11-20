package com.shizijie.beta.auth.serivce;


import com.shizijie.beta.model.ResultBean;

/**
 * @author shizijie
 * @version 2018-06-10 下午7:02
 */
public interface UserService {
    ResultBean userLogin(String username, String password);

    void test();
}
