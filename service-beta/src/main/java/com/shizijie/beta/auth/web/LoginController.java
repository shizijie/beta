package com.shizijie.beta.auth.web;

import com.shizijie.beta.auth.dao.UserDao;
import com.shizijie.beta.auth.serivce.impl.EnumServiceImpl;
import com.shizijie.beta.auth.serivce.impl.UserServiceImpl;
import com.shizijie.beta.auth.utils.MD5Util;
import com.shizijie.beta.auth.vo.UserVO;
import com.shizijie.beta.common.ResultBean;
import com.shizijie.beta.thread.BetaThreadPoolServices;
import com.shizijie.beta.thread.KpiCalcThread;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.*;

@Api(value="user", tags="用户模块")
@RestController
@RequestMapping("/user")
public class LoginController {
    private final static Logger log= LoggerFactory.getLogger(LoginController.class);
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
    public ResultBean login(UserVO vo){
        return userServiceImpl.userLogin(vo.getUserName(), MD5Util.md5(vo.getPassword()));
    }
    @GetMapping("/test")
    public ResultBean test(UserVO vo){
        //EnumServiceImpl.valueOf("LOGIN").test("xl",userDao);
        System.out.println("start");
        ThreadPoolExecutor pool=BetaThreadPoolServices.getThreadPool("TEST",4);
        for(int i=0;i<10;i++){
            Future future=pool.submit(new KpiCalcThread(i));
        }
        pool.shutdown();
        try {
            System.out.println("=============="+pool.awaitTermination(1, TimeUnit.DAYS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(pool.isShutdown());
        System.out.println(pool.isTerminated());
        System.out.println("end");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(pool);

        CompletableFuture<String> completableFuture=new CompletableFuture();
        new Thread(new Runnable() {
            @Override public void run() {
                //模拟执行耗时任务
                System.out.println("task doing...");
                try {
                    Thread.sleep(3000);
                }
                catch (InterruptedException e) { e.printStackTrace(); }
                //告诉completableFuture任务已经完成
                completableFuture.complete("result");
            }
        }).start();
        //获取任务结果，如果没有完成会一直阻塞等待
        String result= null;
        try {
            result = completableFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("计算结果:"+result);




        return ResultBean.success("ok");
    }

}
