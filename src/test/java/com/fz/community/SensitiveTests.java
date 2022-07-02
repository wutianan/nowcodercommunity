package com.fz.community;

import com.fz.community.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author zxf
 * @date 2022/6/18
 */

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTests {

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void test1(){
        String s = "不要赌博，不要嫖娼，不要吸毒，可以开票，拉";
        String filter = sensitiveFilter.filter(s);
        System.out.println(filter);

        s = "不要!赌!博?，不要嫖???娼，不要吸asd毒，可以开票，拉";
        filter = sensitiveFilter.filter(s);
        System.out.println(filter);

    }
}
