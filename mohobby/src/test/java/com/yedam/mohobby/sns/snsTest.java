package com.yedam.mohobby.sns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yedam.mohobby.mapper.sns.SnsMapper;
import com.yedam.mohobby.service.sns.SnsPostVO;
import com.yedam.mohobby.web.sns.SnsController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/*-context.xml")
public class snsTest {
	@Autowired
	SnsMapper mapper;
	
	@Autowired
	SnsController controller;
	
	@Test
	public void testFeedUpdate() {
		SnsPostVO vo = new SnsPostVO();
		vo.setPostId(1);
		vo.setContent("수정테스트");
		vo.setHashtag("수정해시태그");
		mapper.updateFeed(vo);
	}
}
