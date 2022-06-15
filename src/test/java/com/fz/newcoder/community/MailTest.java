package com.fz.newcoder.community;

import com.fz.newcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author zxf
 * @date 2022/6/12
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Test
    public void sendMailTest(){
        mailClient.sendMail("18976329711@163.com","test","这是一个测试邮件");
    }

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testHtmlMail(){
        Context context = new Context();
        context.setVariable("username","张三");
        String content = templateEngine.process("mail/demo",context);
        System.out.println(content);
        mailClient.sendMail("18976329711@163.com","html",content);


    }
}
