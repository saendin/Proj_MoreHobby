package com.yedam.mohobby.serviceImpl.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.user.MypageClassMapper;
import com.yedam.mohobby.service.classes.ClassesVO;
import com.yedam.mohobby.service.user.MypageClassService;
import com.yedam.mohobby.service.user.MypageClassVO;

@Service
public class MypageClassServiceImpl implements MypageClassService {
	
	@Autowired
	MypageClassMapper mapper;
	
	// 유저가 공개중안 강의 목록(강사만)
	@Override
	public List<MypageClassVO> getProfileClasses(String memberId) {
		return mapper.getProfileClasses(memberId);
	}
	
	// 유저가 공개중인 강의 목록 수정
	@Override
	public void updateProfileClasses(ClassesVO classesVO) {
		mapper.updateProfileClasses(classesVO);
	}

	// 유저가 운영중인 강의 목록(카테고리별 조회)
	@Override
	public List<MypageClassVO> getManageClasses(String memberId, String keywordId) {
		HashMap<String, String> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("keywordId", keywordId);
		return mapper.getManageClasses(map);
	}

	// 유저가 참여중인 강의 목록 (카테고리별 조회)
	@Override
	public List<MypageClassVO> getTakeClasses(String memberId, String keywordId) {
		HashMap<String, String> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("keywordId", keywordId);
		return mapper.getTakeClasses(map);
	}
}
