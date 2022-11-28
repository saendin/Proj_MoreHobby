package com.yedam.mohobby.service.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface MypageChallengeService {
	// 유저가 참여중인 챌린지 목록 (카테고리별 조회)
	public List<MypageChallVO> getJoinChall(String memberId, String keywordId);
		
	// 유저가 완료한 챌린지 목록(카테고리별 조회, 성공한 챌린지 목록)
	public List<MypageChallVO> getCompleteChall(MypageChallVO mypageChallVO);
}
