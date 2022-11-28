package com.yedam.mohobby.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.mohobby.service.communal.KeywordVO;

@Service
public interface MemPickKeywordService {
	// 전체 관심사 대분류 목록
	public List<KeywordVO> getAllCatg();
	
	// 회원 관심사 list select
	public List<MemPickKeywordVO> selectPickKeywordList(String memberId);
		
	// 회원 관심사 insert
	public void insertPickKeyword(MemPickKeywordVO memPickKeywordVO);
		
	// 회원 관심사 delete
	public void deletePickKeyword(MemPickKeywordVO memPickKeywordVO);
}
