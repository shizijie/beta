package com.shizijie.beta.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

public class Test {
    @Autowired
    private RedisTemplate redisTemplate;

    public static void main(String[] args) {
        byte[] bytes= "-1网".getBytes();
        System.out.println(bytes);
        System.out.println(new String(bytes));
        //redis key 存储为byte[]
    }

    public void test(){
        redisTemplate.execute((RedisConnection connection) -> test1(connection));
    }
    
    public String test1(RedisConnection connection){
        return null;
    }
}
