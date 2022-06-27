package com.fz.newcoder.community.controller;

import com.fz.newcoder.community.entity.Message;
import com.fz.newcoder.community.entity.Page;
import com.fz.newcoder.community.entity.User;
import com.fz.newcoder.community.service.MessageService;
import com.fz.newcoder.community.service.UserService;
import com.fz.newcoder.community.util.CommunityUtil;
import com.fz.newcoder.community.util.HostHolder;
import com.fz.newcoder.community.util.SensitiveFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zxf
 * @date 2022/6/26
 */
@Controller
public class MessageController {

    @Resource
    private MessageService messageService;

    @Resource
    private UserService userService;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private SensitiveFilter sensitiveFilter;

    @RequestMapping(value = "/letter/list",method = RequestMethod.GET)
    public String getLetterList(Model model, Page page){
        User user = hostHolder.getUser();
        //设置分页信息
        page.setRows(messageService.findConversionCount(user.getId()));
        page.setLimit(5);
        page.setPath("/letter/list");
        //会话列表
        List<Message> conversationList = messageService.findConversations(user.getId(), page.getOffset(), page.getLimit());
        List<Map<String,Object>> conversations = new ArrayList<>();
        if(conversationList != null){
            for(Message message : conversationList){
                Map<String,Object> map = new HashMap<>();
                map.put("conversation",message);
                map.put("letterCount",messageService.findLetterCount(message.getConversationId()));
                map.put("unreadCount",messageService.selectLetterUnreadCount(user.getId(),message.getConversationId()));
                int targetId = user.getId() == message.getFromId()?message.getToId():message.getFromId();
                map.put("target",userService.findUserById(targetId));
                conversations.add(map);
            }
        }
        model.addAttribute("conversations",conversations);
        //查询总的未读消息数量
        int letterUnreadCount = messageService.selectLetterUnreadCount(user.getId(), null);
        model.addAttribute("letterUnreadCount",letterUnreadCount);

        return "/site/letter";
    }

    @RequestMapping(value = "/letter/detail/{conversationId}",method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationId") String conversationId, Model model,Page page){
        page.setRows(messageService.findLetterCount(conversationId));
        page.setLimit(5);
        page.setPath("/letter/detail/"+ conversationId);

        List<Message> letterList = messageService.findLetters(conversationId, page.getOffset(), page.getLimit());
        List<Map<String,Object>> letters = new ArrayList<>();
        if(letterList != null){
            for(Message message : letterList) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("letter", message);
                map.put("fromUser", userService.findUserById(message.getFromId()));
                letters.add(map);
            }
        }
        if(letterList != null){
            Message message = letterList.get(0);
            int targetId = hostHolder.getUser().getId() == message.getFromId()? message.getToId():message.getFromId();
            model.addAttribute("target",userService.findUserById(targetId));
        }
        model.addAttribute("letters",letters);
        //读了后要将信息的未读状态更新为已读
        List<Integer> ids = getLettersIds(letterList);
        if(!ids.isEmpty()){
            messageService.readMessage(ids);
        }
        return "/site/letter-detail";
    }

    public List<Integer> getLettersIds(List<Message> letterList){
        List<Integer> ids = new ArrayList<>();
        if(letterList != null){
            for(Message message : letterList){
                if(hostHolder.getUser().getId() == message.getToId() && message.getStatus()==0){
                    ids.add(message.getId());
                }
            }
        }
        return  ids;
    }

    @RequestMapping(value = "/letter/send",method = RequestMethod.POST)
    @ResponseBody
    public String sendLetter(String toName,String content){
        User target = userService.findUserByName(toName);
        if(target == null){
            return CommunityUtil.getJSONString(1,"目标用户不存在");
        }
        Message message = new Message();
        message.setContent(content);
        message.setCreateTime(new Date());
        message.setStatus(0);
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(target.getId());
        if(message.getFromId() > message.getToId()){
            message.setConversationId(message.getToId() + "_" + message.getFromId());
        }else{
            message.setConversationId(message.getFromId() + "_" + message.getToId());
        }
        messageService.addMessage(message);
        return CommunityUtil.getJSONString(0);


    }

}
