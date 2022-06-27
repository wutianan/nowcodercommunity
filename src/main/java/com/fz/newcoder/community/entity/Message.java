package com.fz.newcoder.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author zxf
 * @date 2022/6/26
 */
@Data
public class Message {

    private int id;
    private int fromId;
    private int toId;
    private String conversationId;
    private String content;
    private int status;
    private Date createTime;

}
