package com.yedam.mohobby.serviceImpl.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.admin.AdminSnsMapper;
import com.yedam.mohobby.service.admin.AdminSnsService;
import com.yedam.mohobby.service.communal.HashtagVO;

@Service
public class AdminSnsServiceImpl implements AdminSnsService {

	@Autowired
	AdminSnsMapper mapper;

	@Override
	public List<HashtagVO> top10Hashtag() {
		return mapper.top10Hashtag();
	}
}
