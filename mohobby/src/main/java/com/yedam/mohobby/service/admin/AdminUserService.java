package com.yedam.mohobby.service.admin;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface AdminUserService {

	// 각 달별로 가입한 회원 수
	public int signUpMemList(List<HashMap<String, String>> dateList);
	
	// 각 달별로 탈퇴한 회원 수
	public int leaveMemList(List<HashMap<String, String>> dateList);
}



