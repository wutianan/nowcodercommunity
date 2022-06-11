package com.fz.newcoder.community.dao;

import com.fz.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zxf
 * @date 2022/6/9
 */
@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset,int limit);

    /**
     * 查询共有多少条记录
     * 当需要动态拼接，且只有一个参数时，需要加上@Param注解，取别名，否则报错
     * @param userId 用户id
     * @return
     */
    int selectDiscussPostRows(@Param("userId") int userId);


}
