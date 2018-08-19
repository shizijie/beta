package com.shizijie.beta.auth.serivce.impl;

import com.shizijie.beta.auth.dao.UserDao;
import com.shizijie.beta.auth.dto.AuthDTO;
import com.shizijie.beta.auth.dto.UserDTO;
import com.shizijie.beta.auth.serivce.EnumService;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shizijie
 * @version 2018-07-21 上午8:45
 */
public enum EnumServiceImpl implements EnumService {
    LOGIN {
        @Override
        public void test(String str,UserDao userDao) {
            UserDTO user=userDao.getUserByNameAndPwd(str,"2");
            System.out.println("login");
            System.out.println(user==null);
            System.out.println(user);
        }
    },
    AUTH{
        @Override
        public void test(String str,UserDao userDao) {
            List<AuthDTO> list=userDao.getAuthListByUserId(str);
            System.out.println("auth");
            System.out.println(list);
        }
    };
}
