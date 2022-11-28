package com.yedam.mohobby.serviceImpl.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.main.MainMapper;
import com.yedam.mohobby.service.main.MainChallVO;
import com.yedam.mohobby.service.main.MainClassVO;
import com.yedam.mohobby.service.main.MainService;
import com.yedam.mohobby.service.moim.MoimVO;
import com.yedam.mohobby.service.sns.SnsPostVO;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	MainMapper mapper;
	
	// sns인기글 조회
	@Override
	public List<SnsPostVO> SnsHighLikesList() {
		return mapper.SnsHighLikesList();
	}

	// 인기순 모임 조회
	@Override
	public List<MoimVO> MoimPopularList() {
		return mapper.MoimPopularList();
	}

	// 모집중인 강의 조회
	@Override
	public List<MainClassVO> collectClassList() {
		return mapper.collectClassList();
	}

	// 챌린지 실시간 인증샷 조회
	@Override
	public List<MainChallVO> challNewCertList() {
		return mapper.challNewCertList();
	}

	// sns키워드 검색 조회
	@Override
	public List<SnsPostVO> searchSnsKeyword(String searchText) {
		return mapper.searchSnsKeyword(searchText);
	}

	// moim키워드 검색 조회
	@Override
	public List<MoimVO> searchMoimKeyword(String searchText) {
		return mapper.searchMoimKeyword(searchText);
	}

	// 강의 키워드 검색 조회
	@Override
	public List<MainClassVO> searchClassKeyword(String searchText) {
		return mapper.searchClassKeyword(searchText);
	}

}
