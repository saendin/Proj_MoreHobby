package com.yedam.mohobby.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yedam.mohobby.service.classes.ClassInfoRequestVO;
import com.yedam.mohobby.service.classes.ClassService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/*-context.xml")
public class ClassTest {
	
	@Autowired
	ClassService classService;
	
	//@Test
	public void saveClassInfo() {
		ClassInfoRequestVO req = new ClassInfoRequestVO();
		req.setContent("<h1>Hello, World!</h1>");
		req.setFilename("3");
		//classController.saveClassInfo(req);
	}
	
	@Test
	public void getChapterList() {
		classService.getChapterList(1, "user1");
	}
	

}
