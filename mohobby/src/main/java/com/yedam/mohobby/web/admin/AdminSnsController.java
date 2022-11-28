package com.yedam.mohobby.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.admin.AdminSnsService;
import com.yedam.mohobby.service.communal.HashtagVO;

/**
 * @create 2002/10/06
 * @author gabin
 * @title 관리자 챌린지
 */
@RestController
@CrossOrigin(origins = "*")
public class AdminSnsController {

	@Autowired
	AdminSnsService service;
	
	/**
	 * @title top10 해시태그 가져오
	 * @return List<HashtagVO>
	 */
	@GetMapping("/adminHashtag")
	public List<HashtagVO> top10Hashtag() {
		return service.top10Hashtag();
	}
}
