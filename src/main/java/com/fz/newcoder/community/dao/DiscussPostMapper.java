package com.fz.newcoder.community.dao;


import com.fz.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);



    /**
     *  '@Param注解用于给参数取别名,
     *  如果只有一个参数,并且在<if>里使用,则必须加别名.
     * @param userId
     * @return
     */
    int selectDiscussPostRows(@Param("userId") int userId);

    /**
     * 插入文章
     * @param discussPost 插入文章
     * @return 成功的行数
     */
    int insertDiscussPost(DiscussPost discussPost);

    /**
     * 查询文章
     * @param id
     * @return
     */
    DiscussPost selectDiscussPostById(int id);

    int updateDiscussPostComment(int id, int commentCount);

}
