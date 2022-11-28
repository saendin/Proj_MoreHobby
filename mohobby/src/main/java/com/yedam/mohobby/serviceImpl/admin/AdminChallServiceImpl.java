package com.yedam.mohobby.serviceImpl.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.admin.AdminChallMapper;
import com.yedam.mohobby.service.admin.AdminChallService;

@Service
public class AdminChallServiceImpl implements AdminChallService {
	
	@Autowired
	AdminChallMapper mapper;
}
