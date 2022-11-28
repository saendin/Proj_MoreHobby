package com.yedam.mohobby.serviceImpl.admin;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.admin.AdminUserMapper;
import com.yedam.mohobby.service.admin.AdminUserService;
import com.yedam.mohobby.service.admin.FlaggingVO;

@Service
public class AdminUserServiceImpl implements AdminUserService {

	@Autowired
	AdminUserMapper mapper;

	// 각 달별로 가입한 회원 수
	@Override
	public int signUpMemList(List<HashMap<String, String>> dateList) {
		return mapper.signUpMemList(dateList);
	}

	// 각 달별로 탈퇴한 회원 수
	@Override
	public int leaveMemList(List<HashMap<String, String>> dateList) {
		return mapper.leaveMemList(dateList);
	}

	
}
