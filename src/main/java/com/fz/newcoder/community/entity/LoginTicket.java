package com.fz.newcoder.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author zxf
 * @date 2022/6/14
 */
@Data
public class LoginTicket {

    private int id;
    private int userId;
    private String ticket;
    private int status;
    private Date expired;
}
