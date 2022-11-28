package com.yedam.mohobby.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.admin.AdminFlagService;
import com.yedam.mohobby.service.admin.FlaggingVO;
import com.yedam.mohobby.service.moim.MoimVO;


@RestController
@CrossOrigin(origins = "*")
public class AdminFlagController {
	
	@Autowired
	AdminFlagService service;
	
	// 신고된 유저 목록
		@GetMapping("/admimflaguser")
		public List<FlaggingVO> flagUserList() {
			return service.flagUserList();
		}
		
		// 신고된 유저 정보 업데이트
		@PutMapping("/updateFlag")
		public void updateFlag(@RequestBody FlaggingVO flaggingVO) {
			service.updateFlag(flaggingVO);
		}
		
		// 신고하기
		@PostMapping("/flagging")
		public void insertFlagging(@RequestBody FlaggingVO flaggingVO) {
			service.insertFlagging(flaggingVO);
		}
		

		/**
		 * @title 신고된 소모임 목록
		 * @return List<FlaggingVO>
		 */
		@GetMapping("/adminflagmoim")
		public List<FlaggingVO> flagMoimList() {
			return service.flagMoimList();
		}
		
		
		// 신고 제거
		@DeleteMapping("/flagging/{flagId}")
		public void deleteFlag(@PathVariable int flagId) {
			service.deleteFlag(flagId);
		}
		
		// 신고된 소모임 block
		@PutMapping("/updateMoimOpen")
		public void updateMoimOpen(@RequestBody MoimVO moimVO) {
			service.updateMoimOpen(moimVO);
		}
		
		// 신고된 유저 block
		
		
		//  신고 코드에 따라서 검색
		@GetMapping("/searchCodeList/{flagCode}")
			public List<FlaggingVO> getFlagByCode(@PathVariable String flagCode) {
				return service.getFlagByCode(flagCode);
			}
}
