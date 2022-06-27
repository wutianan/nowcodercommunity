package com.fz.newcoder.community.controller;

import com.fz.newcoder.community.entity.DiscussPost;
import com.fz.newcoder.community.entity.Page;
import com.fz.newcoder.community.entity.User;
import com.fz.newcoder.community.service.DiscussPostService;
import com.fz.newcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zxf
 * @date 2022/6/10
 */
@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        page.setRows(discussPostService.selectDiscussPostRows(0));
        page.setPath("index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if(list != null){
            for(DiscussPost post:list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post",post);
                int id = post.getUserId();
                User user = userService.findUserById(id);
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }

    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public String getErrorPage(){
        return "/error/500";
    }
}
