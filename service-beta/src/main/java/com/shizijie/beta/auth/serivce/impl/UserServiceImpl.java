package com.shizijie.beta.auth.serivce.impl;

import com.shizijie.beta.auth.dao.UserDao;
import com.shizijie.beta.auth.dto.AuthDTO;
import com.shizijie.beta.auth.dto.UserDTO;
import com.shizijie.beta.auth.serivce.UserService;
import com.shizijie.beta.common.ResultBean;
import com.shizijie.beta.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author shizijie
 * @version 2018-06-10 下午7:02
 */
@Service
@Transactional(rollbackFor = { Exception.class })
@Slf4j
public class UserServiceImpl implements UserService {
    private final static String TOKEN="token_";
    @Autowired
    UserDao userDao;
    @Autowired
    RedisService redisService;
    @Autowired
    RedisTemplate redisTemplate;
    @Override
    public ResultBean userLogin(String username, String password) {
        UserDTO userDTO=userDao.getUserByNameAndPwd(username,password);
        if(userDTO!=null){
            //查询权缓存redis
            List<AuthDTO> authDTOList=userDao.getAuthListByUserId(userDTO.getUserId());
            if(authDTOList==null||authDTOList.size()==0){
                return ResultBean.fail("您暂无任何权限！");
            }else{
                List<String> authList=new ArrayList<>();
                for(AuthDTO auth_1:authDTOList){
                    for(AuthDTO auth_2:auth_1.getAuthDTOList()){
                        for(AuthDTO auth_3:auth_2.getAuthDTOList()){
                            authList.add(auth_3.getMenuUrl());
                        }
                    }
                }
                userDTO.setAuthList(authList);
                String uuid= UUID.randomUUID().toString().replace("-","");
                redisService.set(uuid,userDTO,30*60*1000L);
                Map<String,Object> map=new HashMap<>();
                map.put("token",uuid);
                map.put("authList",authDTOList);
                return ResultBean.success(map);
            }
        }else{
            return ResultBean.fail("帐号/密码错误！");
        }
    }

    @Override
    public void test() {
        System.out.println(System.currentTimeMillis());
        UserDTO user=new UserDTO();
        user.setUserName("1");
        user.setPassword("2");
        int num=userDao.insertUser(user);
        String sre=null;
        //System.out.println(sre.equals("1"));
        for(int i=0;i<100000000;i++){
            List<AuthDTO> authDTOList=userDao.getAuthListByUserId("1");
        }
        System.out.println(System.currentTimeMillis());
    }
}
