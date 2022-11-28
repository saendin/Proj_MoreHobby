package com.yedam.mohobby.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.mohobby.service.sns.SnsFollowVO;
import com.yedam.mohobby.service.sns.SnsPostVO;

@Service
public interface MypageSnsService {
	// 유저의 팔로워 목록
	public List<SnsFollowVO> getFollower(String memberId);
		
	// 유저의 팔로잉 목록
	public List<SnsFollowVO> getFollowing(String memberId);
		
	// sns 유저가 작성한 게시글 목록
	public List<SnsPostVO> getPost(String memberId);
		
	// sns 유저가 찜한 게시글 목록
	public List<SnsPostVO> getJjim(String memberId);
}
