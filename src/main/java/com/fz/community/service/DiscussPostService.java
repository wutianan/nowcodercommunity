package com.fz.community.service;

import com.fz.community.dao.DiscussPostMapper;
import com.fz.community.entity.DiscussPost;
import com.fz.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author zxf
 * @date 2022/6/10
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(userId, offset, limit);
        return discussPosts;
    }

    public int selectDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    public int addDiscussPost(DiscussPost discussPost){
        if(discussPost == null){
            throw new IllegalArgumentException("参数不能为空");
        }
        //转义html标签
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        //做敏感词进行过滤
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));
        return discussPostMapper.insertDiscussPost(discussPost);
    }

    public DiscussPost selectDiscussPostById(int id){
        return discussPostMapper.selectDiscussPostById(id);
    }

    public int updateDiscussPostCount(int id, int commentCount){
        return discussPostMapper.updateDiscussPostComment(id,commentCount);
    }

}
