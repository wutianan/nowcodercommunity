package com.fz.newcoder.community.dao;

import com.fz.newcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author zxf
 * @date 2022/6/14
 */
@Mapper
public interface LoginTicketMapper {

    @Insert({
            "insert into newcodercommnuity.login_ticket (user_id, ticket, status, expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from newcodercommnuity.login_ticket where ticket = #{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "update newcodercommnuity.login_ticket set status=#{status} where ticket = #{ticket}"
    })
    int updateStatus(String ticket,int status);
}
