package com.yedam.mohobby.mapper.main;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.mohobby.service.main.MainChallVO;
import com.yedam.mohobby.service.main.MainClassVO;
import com.yedam.mohobby.service.moim.MoimVO;
import com.yedam.mohobby.service.sns.SnsPostVO;

@Mapper
public interface MainMapper {

	// sns인기글 조회
	public List<SnsPostVO> SnsHighLikesList();
	
	// 인기순 모임 조회
	public List<MoimVO> MoimPopularList();
	
	// 모집중인 강의 조회
	public List<MainClassVO> collectClassList();
	
	// 챌린지 실시간 인증샷 조회
	public List<MainChallVO> challNewCertList();
	
	// sns키워드 검색 조회
	public List<SnsPostVO> searchSnsKeyword(String searchText);
	
	// moim키워드 검색 조회
	public List<MoimVO> searchMoimKeyword(String searchText);
	
	// 강의 키워드 검색 조회
	public List<MainClassVO> searchClassKeyword(String searchText);
}
