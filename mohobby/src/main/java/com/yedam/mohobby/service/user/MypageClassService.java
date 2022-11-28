package com.yedam.mohobby.service.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.mohobby.service.classes.ClassesVO;

@Service
public interface MypageClassService {
	// 유저가 공개중안 강의 목록(강사만)
	 public List<MypageClassVO> getProfileClasses(String memberId);
	 
	 // 유저가 공개중인 강의 목록 수정
	 public void updateProfileClasses(ClassesVO classesVO);
	 
	 // 유저가 운영중인 강의 목록(카테고리별 조회)
	 public List<MypageClassVO> getManageClasses(String memberId, String keywordId);
	 
	 // 유저가 참여중인 강의 목록 (카테고리별 조회)
	 public List<MypageClassVO> getTakeClasses(String memberId, String keywordId);

}
