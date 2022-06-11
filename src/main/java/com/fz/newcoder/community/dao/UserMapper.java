package com.fz.newcoder.community.dao;

import com.fz.newcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User findUserById(int userId);
}
