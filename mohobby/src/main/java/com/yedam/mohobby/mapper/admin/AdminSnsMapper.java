package com.yedam.mohobby.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.mohobby.service.communal.HashtagVO;

@Mapper
public interface AdminSnsMapper {

	// top 10 해시태그
	public List<HashtagVO> top10Hashtag();
}
