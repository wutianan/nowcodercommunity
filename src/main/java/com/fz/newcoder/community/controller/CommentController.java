package com.fz.newcoder.community.controller;

import com.fz.newcoder.community.entity.Comment;
import com.fz.newcoder.community.service.CommentService;
import com.fz.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;
import java.util.Date;

/**
 * @author zxf
 * @date 2022/6/24
 */

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private HostHolder holder;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){

        comment.setCreateTime(new Date());
        comment.setStatus(0);
        comment.setUserId(holder.getUser().getId());
        commentService.insertComment(comment);

        return "redirect:/discuss/detail/"+discussPostId;
    }
}
