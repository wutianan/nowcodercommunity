package com.fz.newcoder.community;

import com.fz.newcoder.community.util.CommunityUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author zxf
 * @date 2022/6/11
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LoggerTest {

    private static final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void testLogger(){
        System.out.println(logger.getName());

        logger.error("error log");
        logger.debug("debug log");
        logger.warn("warn log");
        logger.info("info log");
    }

    @Test
    public void testPassword(){
        String s = CommunityUtil.md5("123167f9");
        System.out.println(s);
    }
}
