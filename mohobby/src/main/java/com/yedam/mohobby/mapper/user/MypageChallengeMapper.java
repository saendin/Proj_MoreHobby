package com.yedam.mohobby.mapper.user;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.mohobby.service.user.MypageChallVO;

@Mapper
public interface MypageChallengeMapper {
	
	// 유저가 참여중인 챌린지 목록 (카테고리별 조회)
	public List<MypageChallVO> getJoinChall(HashMap<String, String> map);
	
	// 유저가 완료한 챌린지 목록(카테고리별 조회, 성공한 챌린지 목록)
	public List<MypageChallVO> getCompleteChall(MypageChallVO mypageChallVO);
}
