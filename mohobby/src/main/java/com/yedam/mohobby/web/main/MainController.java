package com.yedam.mohobby.web.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.classes.ClassesVO;
import com.yedam.mohobby.service.main.MainChallVO;
import com.yedam.mohobby.service.main.MainClassVO;
import com.yedam.mohobby.service.main.MainService;
import com.yedam.mohobby.service.moim.MoimVO;
import com.yedam.mohobby.service.sns.SnsPostVO;

@RestController
@CrossOrigin(origins = "*")
public class MainController {

	@Autowired
	MainService service;
	
	/**
	 * @title sns인기글 조회
	 * @return List<SnsPostVO>
	 */
	@GetMapping("/mainsnslist")
	public List<SnsPostVO> SnsHighLikesList() {
		return service.SnsHighLikesList();
	}
	
	/** 
	 * @title 인기순 모임 조회
	 * @return List<MoimVO>
	 */
	@GetMapping("/mainmoimlist")
	public List<MoimVO> MoimPopularList() {
		return service.MoimPopularList();
	}
	
	/**
	 * @title 모집중인 강의 조회
	 * @return List<ClassesVO>
	 */
	@GetMapping("/mainclasslist")
	public List<MainClassVO> collectClassList() {
		return service.collectClassList();
	}
	
	/**
	 * @title 챌린지 실시간 인증샷 조회
	 * @return List<MainChallVO>
	 */
	@GetMapping("/mainchalllist")
	public List<MainChallVO> challNewCertList() {
		return service.challNewCertList();
	}
	
	/**
	 * 
	 * @title sns키워드 검색 조회
	 * @return List<SnsPostVO>
	 */
	@GetMapping("/searchSnskeyword/{searchText}")
	public List<SnsPostVO> searchSnsKeyword(@PathVariable String searchText) {
		return service.searchSnsKeyword(searchText);
	}
	
	/**
	 * 
	 * @title moim키워드 검색 조회
	 * @return List<MoimVO>
	 */
	@GetMapping("/searchMoimkeyword/{searchText}")
	public List<MoimVO> searchMoimKeyword(@PathVariable String searchText) {
		return service.searchMoimKeyword(searchText);
	}
	
	
	/**
	 * 
	 * @title 강의 키워드 검색 조회
	 * @return List<MainClassVO>
	 */
	@GetMapping("/searchClassKeyword/{searchText}")
	public List<MainClassVO> searchClassKeyword(@PathVariable String searchText) {
		return service.searchClassKeyword(searchText);
	}
	
	
	
	
	
	
}
