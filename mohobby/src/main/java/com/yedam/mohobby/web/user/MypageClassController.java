package com.yedam.mohobby.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.classes.ClassesVO;
import com.yedam.mohobby.service.user.MypageClassService;
import com.yedam.mohobby.service.user.MypageClassVO;

/**
 * @create 22/10/05
 * @author gabin
 * @title 회원 마이페이지 강의
 */
@RestController
public class MypageClassController {

	@Autowired
	MypageClassService service;
	
	/**
	 * 
	 * @param memberId
	 * @return List<MypageClassVO>
	 */
	// 유저가 공개중안 강의 목록(강사만)
	@GetMapping("/mypageprofileclasses/{memberId}")
	public List<MypageClassVO> getProfileClasses(@PathVariable String memberId) {
		return service.getProfileClasses(memberId);
	}
	
	/**
	 * 
	 * @param classesVO
	 */
	// 유저가 공개중인 강의 목록 수정
	@PutMapping("/mypageprofileclasses")
	public void updateProfileClasses(@RequestBody ClassesVO classesVO) {
		service.updateProfileClasses(classesVO);
	}
	
	/**
	 * 
	 * @param memberId
	 * @param keywordId
	 * @return List<MypageClassVO>
	 */
	// 유저가 운영중인 강의 목록(카테고리별 조회)
	@GetMapping("/mypagemanageclasses")
	public List<MypageClassVO> getManageClasses(@RequestParam("memberId") String memberId, @RequestParam("keywordId") String keywordId) {
		return service.getManageClasses(memberId, keywordId);
	}
	
	/**
	 * 
	 * @param memberId
	 * @param keywordId
	 * @return List<MypageClassVO>
	 */
	// 유저가 참여중인 강의 목록 (카테고리별 조회)
	@GetMapping("/mypagetakeclasses")
	public List<MypageClassVO> getTakeClasses(@RequestParam("memberId") String memberId, @RequestParam("keywordId") String keywordId) {
		return service.getTakeClasses(memberId, keywordId);
	}
}
