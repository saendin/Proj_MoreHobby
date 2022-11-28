package com.yedam.mohobby.mapper.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.mohobby.service.classes.ClassesVO;
import com.yedam.mohobby.service.user.MypageClassVO;

@Mapper
public interface MypageClassMapper {

// 유저가 공개중안 강의 목록(강사만)
 public List<MypageClassVO> getProfileClasses(String memberId);
 
 // 유저가 공개중인 강의 목록 수정
 public void updateProfileClasses(ClassesVO classesVO);
 
 // 유저가 운영중인 강의 목록(카테고리별 조회)
 public List<MypageClassVO> getManageClasses(HashMap<String, String> map);
 
 // 유저가 참여중인 강의 목록 (카테고리별 조회)
 public List<MypageClassVO> getTakeClasses(HashMap<String, String> map);
}
