package com.yedam.mohobby.service.user;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.mohobby.service.moim.MoimMemberVO;
import com.yedam.mohobby.service.moim.MoimVO;

@Service
public interface MypageMoimService {
	// 유저가 공개중인 소모임 목록
	public List<MoimVO> getProfileMoim(String memberId);
		
	// 유저가 공개중인 소모임 목록 수정
	public void updateProfileMoim(MoimMemberVO moimMemberVO);
		
	// 유저가 참여, 운영중 소모임 목록(카테고리별 조회) / 참여중 memberRole 0/ 운영중 memberRole 1
		public List<MypageMoimVO> getMoim(MypageMoimVO mypageMoimVO);

}
