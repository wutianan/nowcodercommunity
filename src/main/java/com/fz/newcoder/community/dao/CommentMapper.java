package com.fz.newcoder.community.dao;

import com.fz.newcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface CommentMapper {

     List<Comment> selectCommentByEntity(int entityType, int entityId, int offset,int limit);

     int selectCountByEntity(int entityType, int entityId);
}
