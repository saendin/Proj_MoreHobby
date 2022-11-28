package com.yedam.mohobby.serviceImpl.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.user.MypageChallengeMapper;
import com.yedam.mohobby.service.user.MypageChallVO;
import com.yedam.mohobby.service.user.MypageChallengeService;

@Service
public class MypageChallengeServiceImpl implements MypageChallengeService {

	@Autowired
	MypageChallengeMapper mapper;

	@Override
	public List<MypageChallVO> getJoinChall(String memberId, String keywordId) {
		HashMap<String, String> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("keywordId", keywordId);
		return mapper.getJoinChall(map);
	}

	@Override
	public List<MypageChallVO> getCompleteChall(MypageChallVO mypageChallVO) {
		return mapper.getCompleteChall(mypageChallVO);
	}
}
