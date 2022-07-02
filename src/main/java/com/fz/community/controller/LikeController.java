package com.fz.community.controller;

import com.fz.community.entity.User;
import com.fz.community.service.LikeService;
import com.fz.community.util.CommunityConstant;
import com.fz.community.util.CommunityUtil;
import com.fz.community.util.HostHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zxf
 * @date 2022/6/27
 */
@Controller
public class LikeController implements CommunityConstant {

    @Autowired
    private LikeService likeService;

    @Autowired
    private HostHolder hostHolder;


    /**
     * 异步请求，点赞
     * @param entityType
     * @param entityId
     * @return
     */
    @RequestMapping(value = "/like",method = RequestMethod.POST)
    @ResponseBody
    public String like(int entityType, int entityId, int entityUserId , int postId){
        User user = hostHolder.getUser();
        //点赞
        likeService.like(user.getId(),entityType,entityId,entityUserId);
        //点赞数量
        long likeCount = likeService.findEntityLikeCount(entityType, entityId);
        //点赞状态
        int likeStatus = likeService.findEntityLikeStatus(user.getId(), entityType, entityId);
        Map<String,Object> map = new HashMap<>();
        map.put("likeCount",likeCount);
        map.put("likeStatus",likeStatus);

        //触发评论事件
        //if(likeStatus == 1) {
        //    Event event = new Event()
        //            .setTopic(TOPIC_LIKE)
        //            .setUserId(hostHolder.getUser().getId())
        //            .setEntityId(entityId)
        //            .setEntityType(entityType)
        //            .setData("postId", postId)
        //            .setEntityUserId(entityUserId);
        //    eventProducer.fireEvent(event);
        //}
        return CommunityUtil.getJSONString(0,null,map);

    }
}
