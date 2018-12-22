package com.shizijie.beta.auth.dao;

import com.shizijie.beta.annotation.Save;
import com.shizijie.beta.bean.user.dto.AuthDTO;
import com.shizijie.beta.bean.user.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    UserDTO getUserByNameAndPwd(@Param("userName")String userName, @Param("password")String password);

    List<AuthDTO> getAuthListByUserId(@Param("userId")String userId);

    @Save("system")
    int insertUser(UserDTO user);
}
