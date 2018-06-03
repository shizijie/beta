package com.shizijie.beta.config;

import com.shizijie.beta.auth.dao.UserDao;
import com.shizijie.beta.auth.dto.UserDTO;
import com.shizijie.beta.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimerConfig {
    @Autowired
    UserDao userDao;
    @Autowired
    RedisUtils redisUtils;

    //@Scheduled(cron = "0/5 * * * * ?")
    void test(){
        System.out.println("1");
        redisUtils.set("22","xl");
        List<UserDTO> list=userDao.getListUser();
        System.out.println("2");
    }
}
