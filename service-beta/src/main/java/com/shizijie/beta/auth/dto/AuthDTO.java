package com.shizijie.beta.auth.dto;

import lombok.Data;

import java.util.List;

/**
 * @author shizijie
 * @version 2018-06-10 下午10:12
 */
@Data
public class AuthDTO {
    /**菜单id*/
    private String menuId;
    /**菜单url*/
    private String menuUrl;
    /**菜单名称*/
    private String menuName;
    /**子菜单*/
    private List<AuthDTO> authDTOList;
}
