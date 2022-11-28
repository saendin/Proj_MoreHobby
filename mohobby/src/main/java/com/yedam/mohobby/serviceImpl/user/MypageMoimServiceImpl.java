package com.yedam.mohobby.serviceImpl.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.user.MypageMoimMapper;
import com.yedam.mohobby.service.moim.MoimMemberVO;
import com.yedam.mohobby.service.moim.MoimVO;
import com.yedam.mohobby.service.user.MypageMoimService;
import com.yedam.mohobby.service.user.MypageMoimVO;

@Service
public class MypageMoimServiceImpl implements MypageMoimService {
	
	@Autowired
	MypageMoimMapper mapper;

	// 유저가 공개중인 소모임 목록
	@Override
	public List<MoimVO> getProfileMoim(String memberId) {
		return mapper.getProfileMoim(memberId);
	}

	// 유저가 공개중인 소모임 목록 수정
	@Override
	public void updateProfileMoim(MoimMemberVO moimMemberVO) {
		mapper.updateProfileMoim(moimMemberVO);
	}

	// 유저가 참여, 운영중 소모임 목록(카테고리별 조회) / 참여중 memberRole 0/ 운영중 memberRole 1
	@Override
	public List<MypageMoimVO> getMoim(MypageMoimVO mypageMoimVO) {
		return mapper.getMoim(mypageMoimVO);
	}


}
