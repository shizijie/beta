package com.shizijie.beta.auth.web;

import com.shizijie.beta.annotation.Lock;
import com.shizijie.beta.auth.dao.UserDao;
import com.shizijie.beta.auth.serivce.impl.UserServiceImpl;
import com.shizijie.beta.bean.id.IdWorker;
import com.shizijie.beta.bean.port.ServicePort;
import com.shizijie.beta.bean.user.dto.UserDTO;
import com.shizijie.beta.params.BetaParams;
import com.shizijie.beta.utils.MD5Util;
import com.shizijie.beta.auth.vo.UserVO;
import com.shizijie.beta.model.ResultBean;
import com.shizijie.beta.utils.id.SnowFlakeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * @author shizijie
 * @version 2018-12-06 下午8:33
 */
@Api(value="user", tags="用户模块")
@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    UserDao userDao;

    @ApiOperation(value="用户登录",notes="根据用户信息登录验证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String")
    })
    @PostMapping("/login")
    public ResultBean login(@Validated @RequestBody UserVO vo){
        return userServiceImpl.userLogin(vo.getUserName(), MD5Util.md5(vo.getPassword()));
    }
    @GetMapping("/test")
    public ResultBean test(UserVO vo){
//        Task task=new Task();
//        for(int i=0;i<5;i++){
//            new Thread(task).start();
//        }
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return ResultBean.success("1111");
        UserDTO user=new UserDTO();
        user.setUserName("test");
        user.setPassword("2222");
        user.setUserId(SnowFlakeUtils.nextId()+"");
        userDao.insertUser(user);
        System.out.println("ok");
        return ResultBean.success();
    }

}
