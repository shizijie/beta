package com.shizijie.beta.demo.enums;

import com.shizijie.beta.auth.dao.UserDao;
import com.shizijie.beta.auth.dto.AuthDTO;
import com.shizijie.beta.auth.dto.UserDTO;

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

    public static void main(String[] args) {
        System.out.println(EnumTest.valueOf("SUCCESS"));
    }
}
