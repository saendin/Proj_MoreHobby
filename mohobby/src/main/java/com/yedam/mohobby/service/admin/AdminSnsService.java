package com.yedam.mohobby.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yedam.mohobby.service.communal.HashtagVO;

@Service
public interface AdminSnsService {

	// top 10 해시태그
	public List<HashtagVO> top10Hashtag();
}
