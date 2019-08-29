package com.szj.beta.strive.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shizijie
 * @version 2019-08-06 下午11:19
 */
@RestController
public class TestController {
    @GetMapping("/fallback")
    public String fallback() {
        return "服务暂时不可用11111";
    }
}
