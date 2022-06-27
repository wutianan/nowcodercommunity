package com.fz.newcoder.community.service;

import com.fz.newcoder.community.dao.MessageMapper;
import com.fz.newcoder.community.entity.Message;
import com.fz.newcoder.community.util.SensitiveFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import org.unbescape.html.HtmlEscape;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zxf
 * @date 2022/6/26
 */
@Service
public class MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private SensitiveFilter sensitiveFilter;

    public List<Message> findConversations(int userId, int offset,int limit){
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversionCount(int userId){
        return messageMapper.selectConversionCount(userId);
    }

    public List<Message> findLetters(String conversationId, int offset, int limit){
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId){
        return messageMapper.selectLetterCount(conversationId);
    }

    public int selectLetterUnreadCount(int userId, String conversationId){
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    public int addMessage(Message message){
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));

        return messageMapper.insertLetter(message);
    }

    public void readMessage(List<Integer> messageList){

            messageMapper.updateLetterStatus(messageList,1);

    }

}
