package com.shizijie.beta.auth.serivce;

import com.github.pagehelper.PageHelper;
import com.shizijie.beta.auth.dao.UserDao;
import com.shizijie.beta.auth.dto.AuthDTO;
import com.shizijie.beta.auth.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Test {
    @Autowired
    UserDao userDao;
    @Cacheable(value="user",key="#id.toString()")
    public List<AuthDTO> test1(String id){
        System.out.println("--mysql查询-11-");
        return userDao.getAuthListByUserId(id);
    }
    @CachePut(value="user",key="#id.toString()")
    public List<AuthDTO> test2(String id){
        System.out.println("--mysql查询-22-");
        PageHelper.startPage(1,1);
        return userDao.getAuthListByUserId(id);
    }
}
