package com.yedam.mohobby.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.admin.AdminChallService;

/**
 * @create 2002/10/06
 * @author gabin
 * @title 관리자 챌린지
 */
@RestController
public class AdminChallController {

	@Autowired
	AdminChallService service;
}
