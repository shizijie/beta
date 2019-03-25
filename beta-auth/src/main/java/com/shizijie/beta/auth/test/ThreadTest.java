package com.shizijie.beta.auth.test;


import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.shizijie.beta.annotation.ThreadHelp;
import com.shizijie.beta.auth.thread.KpiCalcThread;
import com.shizijie.beta.bean.user.dto.UserDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @author shizijie
 * @version 2018-08-19 下午10:36
 */
@Component
public class ThreadTest {
    @Async
    public Future<UserDTO> test(){
        System.out.println(Thread.currentThread().getName()+"---------------start---------------------");
        UserDTO userDTO;
        userDTO=new UserDTO();
        try {
            userDTO.setUserName("xl");
            userDTO.setPassword("123");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"---------------end---------------------");
        return new AsyncResult<>(userDTO);
    }
}
