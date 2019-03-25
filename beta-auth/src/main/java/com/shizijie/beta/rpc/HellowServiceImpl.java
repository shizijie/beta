package com.shizijie.beta.rpc;

/**
 * @author shizijie
 * @version 2019-03-14 下午9:48
 */
public class HellowServiceImpl implements HellowService {
    @Override
    public String say(String name) {
        return "hellow "+name;
    }
}
