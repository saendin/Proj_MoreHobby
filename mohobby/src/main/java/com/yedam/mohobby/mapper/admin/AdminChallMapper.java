package com.yedam.mohobby.mapper.admin;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.mohobby.service.challenge.ChallengeVO;

@Mapper
public interface AdminChallMapper {

	public void insertChall(ChallengeVO challengeVO);
}
