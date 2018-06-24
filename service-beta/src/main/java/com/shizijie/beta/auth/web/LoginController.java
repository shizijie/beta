package com.shizijie.beta.auth.web;

import com.shizijie.beta.auth.serivce.impl.UserServiceImpl;
import com.shizijie.beta.auth.utils.MD5Util;
import com.shizijie.beta.auth.vo.UserVO;
import com.shizijie.beta.common.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(value="user", tags="用户模块")
@EnableAutoConfiguration
@RestController
@RequestMapping("/user")
public class LoginController {
    private final static Logger log= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserServiceImpl userServiceImpl;

    @ApiOperation(value="用户登录",notes="根据用户信息登录验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @PostMapping("/login")
    public ResultBean test(UserVO vo){
        return userServiceImpl.userLogin(vo.getUserName(), MD5Util.md5(vo.getPassword()));
    }

}
