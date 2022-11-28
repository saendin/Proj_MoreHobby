package com.yedam.mohobby.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.sns.SnsFollowVO;
import com.yedam.mohobby.service.sns.SnsPostVO;
import com.yedam.mohobby.service.user.MypageSnsService;

/**
 * @create 22/10/05
 * @author gabin
 * @title 회원 마이페이지 sns
 */
@RestController
@CrossOrigin(origins = "*")
public class MypageSnsController {
	
	@Autowired
	MypageSnsService service;
	
	/**
	 * 
	 * @param memberId
	 * @return List<String>
	 * @title 유저의 팔로워 목록
	 */
	@GetMapping("/mypagefollower/{memberId}")
	public List<SnsFollowVO> getFollower(@PathVariable String memberId) {
		return service.getFollower(memberId);
	}
	
	/**
	 * @param memberId
	 * @return List<String>
	 * @title 유저의 팔로잉 목록
	 */
	@GetMapping("/mypagefollowing/{memberId}")
	public List<SnsFollowVO> getFollowing(@PathVariable String memberId) {
		return service.getFollowing(memberId);
	}
	
	/**
	 * 
	 * @param memberId
	 * @return List<SnsPostVO>
	 * @title sns 유저가 작성한 게시글 목록
	 */
	@GetMapping("/mypageSnspost/{memberId}")
	public List<SnsPostVO> getPost(@PathVariable String memberId) {
		return service.getPost(memberId);
	}
	
	/**
	 * 
	 * @param memberId
	 * @return List<SnsPostVO>
	 * @title sns 유저가 찜한 게시글 목록
	 */
	@GetMapping("/mypageSnsJjim/{memberId}")
	public List<SnsPostVO> getJjim(@PathVariable String memberId) {
		return service.getJjim(memberId);
	}
	
}
