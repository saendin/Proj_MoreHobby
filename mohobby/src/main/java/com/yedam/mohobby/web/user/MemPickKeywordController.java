package com.yedam.mohobby.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.communal.KeywordVO;
import com.yedam.mohobby.service.user.MemPickKeywordService;
import com.yedam.mohobby.service.user.MemPickKeywordVO;
import com.yedam.mohobby.service.user.MemberVO;

/**
 * @create 22/10/05
 * @author gabin
 * @title 회원 관심사
 */


@RestController
@CrossOrigin(origins = "*")
public class MemPickKeywordController {
	@Autowired
	MemPickKeywordService service;
	
	/**
	 * @title 전체 관심사 대분류 목록
	 */
	/**
	 * 
	 * @param memberId
	 * @return List<MemberPickKeywordVO> 
	 */
	
	@GetMapping("/allCatg")
	public List<KeywordVO> getAllCatg() {
		return service.getAllCatg();
	}
	
	// 회원 관심사 list select
	@GetMapping("/memPickKeyword/{memberId}")
	public List<MemPickKeywordVO> selectPickKeywordList(@PathVariable String memberId) {
		return service.selectPickKeywordList(memberId);
	}
	
	/**
	 * 
	 * @param memPickKeywordVO
	 */
	// 회원 관심사 insert
	@PostMapping("/memPickKeyword")
	public void insertPickKeyword(@RequestBody MemPickKeywordVO memPickKeywordVO) {
		service.insertPickKeyword(memPickKeywordVO);
	}
	
	/**
	 * 
	 * @param memPickKeywordVO
	 */
	// 회원 관심사 delete
	@DeleteMapping("/memPickKeyword/{memberId}/{keywordId}")
	public void deletePickKeyword(@PathVariable String memberId, @PathVariable String keywordId) {
		
		MemPickKeywordVO memPickKeywordVO = new MemPickKeywordVO();
		memPickKeywordVO.setMemberId(memberId);
		memPickKeywordVO.setKeywordId(keywordId);
		service.deletePickKeyword(memPickKeywordVO);
	}
}
