package com.shizijie.beta.auth.dao;

import com.shizijie.beta.auth.dto.UserDTO;

import java.util.List;

public interface UserDao {
    List<UserDTO> getListUser();
}
