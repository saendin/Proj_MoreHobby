package com.yedam.mohobby.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.admin.AdminUserService;
import com.yedam.mohobby.service.admin.FlaggingVO;

/**
 * @create 2002/10/06
 * @author gabin
 * @title 관리자 user
 */
@RestController
@CrossOrigin(origins = "*")
public class AdminUserController {

	@Autowired
	AdminUserService service;
	
	// 각 달별로 가입한 회원 수
//	@GetMapping("/adminsignupmem") 
//	public int signUpMemList(String startDate, String regDate) {
//		List<HashMap<HashMap<String, String>, HashMap<String, String>>> dateList = new ArrayList<>();
//		dateList.add(HashMap<HashMap<"startDate", startdate>, HashMap<"regDate", regdate>>);
//		
//	}
	
	// 각 달별로 탈퇴한 회원 수
	
	
}
