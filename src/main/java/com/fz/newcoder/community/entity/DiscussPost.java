package com.fz.newcoder.community.entity;

import lombok.*;

import java.util.Date;

/**
 * @author zxf
 * @date 2022/6/9
 */
@Data
public class DiscussPost {
    private int id;
    private int userId;
    private String title;
    private String content;
    private int type;
    private int status;
    private Date createTime;
    private int commentCount;
    private double score;


}
