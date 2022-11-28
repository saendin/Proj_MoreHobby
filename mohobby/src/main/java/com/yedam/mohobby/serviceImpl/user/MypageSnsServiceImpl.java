package com.yedam.mohobby.serviceImpl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.user.MypageSnsMapper;
import com.yedam.mohobby.service.sns.SnsFollowVO;
import com.yedam.mohobby.service.sns.SnsPostVO;
import com.yedam.mohobby.service.user.MypageSnsService;

@Service
public class MypageSnsServiceImpl implements MypageSnsService {

	@Autowired
	MypageSnsMapper mapper;
	
	//유저의 팔로워 목록
	@Override
	public List<SnsFollowVO> getFollower(String memberId) {
		return mapper.getFollower(memberId);
	}

	//유저의 팔로잉 목록
	@Override
	public List<SnsFollowVO> getFollowing(String memberId) {
		return mapper.getFollowing(memberId);
	}

	// sns 유저가 작성한 게시글 목록
	@Override
	public List<SnsPostVO> getPost(String memberId) {
		return mapper.getPost(memberId);
	}

	@Override
	public List<SnsPostVO> getJjim(String memberId) {
		return mapper.getJjim(memberId);
	}
}
