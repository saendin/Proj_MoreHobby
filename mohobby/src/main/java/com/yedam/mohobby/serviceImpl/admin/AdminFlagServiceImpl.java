package com.yedam.mohobby.serviceImpl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.admin.AdminFlagMapper;
import com.yedam.mohobby.service.admin.AdminFlagService;
import com.yedam.mohobby.service.admin.FlaggingVO;
import com.yedam.mohobby.service.moim.MoimVO;

@Service
public class AdminFlagServiceImpl implements AdminFlagService {
	
	@Autowired
	AdminFlagMapper mapper;

	// 신고된 유저 목록
		@Override
		public List<FlaggingVO> flagUserList() {
			return mapper.flagUserList();
		}

		// 신고된 유저 정보 업데이트
		@Override
		public void updateFlag(FlaggingVO flaggingVO) {
			mapper.updateFlag(flaggingVO);
		}
		
		// 신고하기
		public void insertFlagging(FlaggingVO flaggingVO) {
			mapper.insertFlagging(flaggingVO);
		}
		
		// 신고된 소모임 목록
		public List<FlaggingVO> flagMoimList() {
			return mapper.flagMoimList();
		}
		
		// 신고 제거
		public void deleteFlag(int flagId) {
			mapper.deleteFlag(flagId);
		}
		
		// 신고된 소모임 block
		public void updateMoimOpen(MoimVO moimVO) {
			mapper.updateMoimOpen(moimVO);
		}
		
		//  신고 코드에 따라서 검색
		public List<FlaggingVO> getFlagByCode(String flagCode) {
			return mapper.getFlagByCode(flagCode);
		}
}
