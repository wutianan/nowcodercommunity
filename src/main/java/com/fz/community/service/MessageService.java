package com.fz.community.service;

import com.fz.community.dao.MessageMapper;
import com.fz.community.entity.Message;
import com.fz.community.util.SensitiveFilter;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

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

    public Message selectLatestNotice(int userId, String topic){
        return messageMapper.selectLatestNotice(userId,topic);
    }

    public int selectNoticeCount(int userId, String topic){
        return messageMapper.selectNoticeCount(userId, topic);
    }

    public int selectNoticeUnreadCount(int userId, String topic){
        return messageMapper.selectNoticeUnreadCount(userId, topic);
    }

    public List<Message> findNotices(int userId, String topic, int offset, int limit) {
        return messageMapper.selectNotices(userId, topic, offset, limit);
    }

}
