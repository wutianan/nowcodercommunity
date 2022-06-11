package com.fz.newcoder.community.service;

import com.fz.newcoder.community.dao.UserMapper;
import com.fz.newcoder.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zxf
 * @date 2022/6/10
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int userId){
        return userMapper.findUserById(userId);
    }
}
