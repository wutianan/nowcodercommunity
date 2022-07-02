package com.fz.community.dao;

import com.fz.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zxf
 * @date 2022/6/26
 */
@Mapper
public interface MessageMapper {

    /**
     * 查询当前用户的会话列表，针对每个会话只返回一条最新的信息
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectConversations(int userId, int offset,int limit);

    /**
     * 查询当前用户的会话数量
     * @param userId
     * @return
     */
    int selectConversionCount(int userId);

    /**
     * 查询某个会话包含的私信列表
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectLetters(String conversationId, int offset, int limit);

    /**
     * 查询某个会话所包含的私信列表
     * @param conversationId
     * @return
     */
    int selectLetterCount(String conversationId);

    /**
     * 查询未读私信的数量
     * @param userId
     * @param conversationId
     * @return
     */
    int selectLetterUnreadCount(int userId, String conversationId);

    int insertLetter(Message message);

    int updateLetterStatus(List<Integer> messageIds, int status);

    //查询某个主题下最新的通知
    Message selectLatestNotice(int userId, String topic);

    //查询某个主题所包含的通知数量
    int selectNoticeCount(int userId, String topic);

    //查询未读的通知数量
    int selectNoticeUnreadCount(int userId,String topic);

    // 查询某个主题所包含的通知列表
    List<Message> selectNotices(int userId, String topic, int offset, int limit);
}
