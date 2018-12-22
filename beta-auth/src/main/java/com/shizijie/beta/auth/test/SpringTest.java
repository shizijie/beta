package com.shizijie.beta.auth.test;

import com.shizijie.beta.auth.dto.UserDTO;
import com.shizijie.beta.utils.thread.ThreadPoolUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTest {
    @Autowired
    ThreadTest threadTest;
    @Test
    public void test(){
        List<Future<UserDTO>> list=new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(threadTest.test());
        }
        System.out.println("执行完毕=-===================================");
        List<UserDTO> list1=ThreadPoolUtils.awaitResult(list,1000);
        System.out.println(list1);
    }
}
