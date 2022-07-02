package com.fz.community;

import com.fz.community.dao.LoginTicketMapper;
import com.fz.community.entity.LoginTicket;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zxf
 * @date 2022/6/14
 */

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest {

    @Resource
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testInsertTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setTicket("asda");
        loginTicket.setExpired(new Date());
        loginTicket.setStatus(0);
        loginTicket.setUserId(111);
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectTicket() {
        LoginTicket asda = loginTicketMapper.selectByTicket("asda");
        System.out.println(asda);
    }

    @Test
    public void testUpdateTicket() {
        loginTicketMapper.updateStatus("asda", 1);
    }

    @Test
    public void getLongestPalindrome() {
        // write code here
        String A = "ABAAB";
        StringBuffer b = new StringBuffer(A);
        StringBuffer reverse = b;
        reverse.reverse();
        System.out.println(b);
        System.out.println(reverse);

    }
}
