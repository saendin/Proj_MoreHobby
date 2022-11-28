package com.yedam.mohobby.serviceImpl.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.user.MemPickKeywordMapper;
import com.yedam.mohobby.service.communal.KeywordVO;
import com.yedam.mohobby.service.user.MemPickKeywordService;
import com.yedam.mohobby.service.user.MemPickKeywordVO;

@Service
public class MemPickKeywordServiceImpli implements MemPickKeywordService {

	@Autowired
	MemPickKeywordMapper mapper;
	
	// 전체 관심사 대분류 목록
	@Override
	public List<KeywordVO> getAllCatg() {
		return mapper.getAllCatg();
	}
	
	// 회원 관심사 list select
	@Override
	public List<MemPickKeywordVO> selectPickKeywordList(String memberId) {
		return mapper.selectPickKeywordList(memberId);
	}

	// 회원 관심사 insert
	@Override
	public void insertPickKeyword(MemPickKeywordVO memPickKeywordVO) {
		mapper.insertPickKeyword(memPickKeywordVO);
	}

	// 회원 관심사 delete
	@Override
	public void deletePickKeyword(MemPickKeywordVO memPickKeywordVO) {
		mapper.deletePickKeyword(memPickKeywordVO);
	}



}
