package com.yedam.mohobby.web;

import java.net.MalformedURLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yedam.mohobby.web.iamport.IamportController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/*-context.xml")
public class IamportTest {
	
	@Autowired
	IamportController iamport;
	
	@Test
	public void Test() throws MalformedURLException {
		iamport.requestCertification("imp_072529125127");
	}
}
