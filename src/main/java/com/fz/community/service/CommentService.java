package com.fz.community.service;

import com.fz.community.dao.CommentMapper;
import com.fz.community.entity.Comment;
import com.fz.community.util.CommunityConstant;
import com.fz.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author zxf
 * @date 2022/6/18
 */
@Service
public class CommentService implements CommunityConstant {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Autowired
    private DiscussPostService discussPostService;

    public List<Comment> findCommentByEntity(int entityType, int entityId, int offset, int limit){
        return commentMapper.selectCommentByEntity(entityType, entityId, offset, limit);
    }

    public int findCommentCount(int entityType,int entityId){
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

    /**
     * 插入评论，因为涉及到插入评论和更新文章评论数量两个dml操作，所以需要加事务管理
     * @param comment
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public int insertComment(Comment comment){
        if(comment == null){
            throw new IllegalArgumentException("评论不能为空");
        }
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveFilter.filter(comment.getContent()));
        //插入评论
        int rows = commentMapper.insertComment(comment);
        //更新文章评论数量
        if(comment.getEntityType() == ENTITY_TYPE_POST){
           int count = commentMapper.selectCountByEntity(ENTITY_TYPE_POST,comment.getEntityId());
           discussPostService.updateDiscussPostCount(comment.getEntityId(),count);
        }
        return rows;

    }

    public Comment findCommentById(int entityId) {
        return commentMapper.findCommentById(entityId);
    }
}
