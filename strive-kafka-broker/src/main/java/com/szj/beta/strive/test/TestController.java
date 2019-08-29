package com.szj.beta.strive.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shizijie
 * @version 2019-08-06 下午11:19
 */
@RestController
public class TestController {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @GetMapping("/test")
    public void test(){
        System.out.println("111");
        kafkaTemplate.send("xltopic",System.currentTimeMillis()+"");
        System.out.println("222");
    }
}
