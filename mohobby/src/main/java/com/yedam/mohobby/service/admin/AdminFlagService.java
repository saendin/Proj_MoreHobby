package com.yedam.mohobby.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.mohobby.service.moim.MoimVO;

@Service
public interface AdminFlagService {
	// 신고된 유저 목록
		public List<FlaggingVO> flagUserList();
		
		// 신고 정보 업데이트
		public void updateFlag(FlaggingVO flaggingVO);

		//신고하기
		public void insertFlagging(FlaggingVO flaggingVO);
		
		// 신고된 소모임 목록
		public List<FlaggingVO> flagMoimList();

		// 신고 제거
		public void deleteFlag(int flagId);
		
		// 신고된 소모임 block
		public void updateMoimOpen(MoimVO moimVO);
		
		//  신고 코드에 따라서 검색
		public List<FlaggingVO> getFlagByCode(String flagCode);
}
