package com.fz.community.controller;

import com.fz.community.entity.Comment;

import com.fz.community.service.CommentService;
import com.fz.community.service.DiscussPostService;
import com.fz.community.util.CommunityConstant;
import com.fz.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @author zxf
 * @date 2022/6/24
 */

@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {

    @Autowired
    private HostHolder holder;

    @Autowired
    private CommentService commentService;

    @Autowired
    private DiscussPostService discussPostService;



    @RequestMapping(value = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){

        comment.setCreateTime(new Date());
        comment.setStatus(0);
        comment.setUserId(holder.getUser().getId());
        commentService.insertComment(comment);



        return "redirect:/discuss/detail/"+discussPostId;
    }
}
