package com.fz.newcoder.community;

import com.fz.newcoder.community.dao.DiscussPostMapper;
import com.fz.newcoder.community.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
class CommunityApplicationTests {

	@Test
	void contextLoads() {
	}

}
