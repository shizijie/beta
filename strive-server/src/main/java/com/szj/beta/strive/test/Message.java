package com.szj.beta.strive.test;

import lombok.Data;

import java.util.Date;

/**
 * @author shizijie
 * @version 2019-08-25 下午9:24
 */
@Data
public class Message {
    private Long id;    //id

    private String msg; //消息

    private Date sendTime;  //时间戳
}
