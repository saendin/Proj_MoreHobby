package com.yedam.mohobby.serviceImpl.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.user.MemberMapper;
import com.yedam.mohobby.service.admin.MemberVO;
import com.yedam.mohobby.service.communal.KeywordVO;
import com.yedam.mohobby.service.user.MemberService;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper mMapper;
	
	// 회원 한명 정보 가져오기(회원 마이페에지, 회원 아이디 있는지 조회)
	@Override
	public MemberVO getMember(String memberId) {
		return mMapper.getMember(memberId);
	}
	
	// 로그인 아이디, 비밀번호가 일치하는지 체크
	public MemberVO checkIdPassword(MemberVO memberVO) {
		return mMapper.checkIdPassword(memberVO);
	}

	// 이메일 통해서 회원인지 조회
	public MemberVO getMemberByEmail(String email) {
		return mMapper.getMemberByEmail(email);
	}
	
	// 회원정보 전체 조회
	public List<MemberVO> getAllMember() {
		return mMapper.getAllMember();
	}
	
	// 지역 전체 조회
	public List<KeywordVO> getRegion() {
		return mMapper.getRegion();
	}
	
	//회원가입 insert
	@Override
	public void insertMember(MemberVO memberVO) {
		mMapper.insertMember(memberVO);
	}
	
	// 회원 프로필 사진 수정
	public void updateProfile(MemberVO memberVO) {
		System.out.println(memberVO.getProfileImg());
		if (memberVO.getProfileImg().equals("nullnull") || memberVO.getProfileImg() == null) {
			memberVO.setProfileImg("comfuck.jpg");
		} 
		mMapper.updateProfile(memberVO);
	}

	// 회원탈퇴 날짜 update
	@Override
	public void deleteMember(String memberId) {
		mMapper.deleteMember(memberId);
	}

	// 회원정보 수정 update
	@Override
	public void updateMember(MemberVO memberVO) {
		try {
			if (memberVO.getProfileImg().equals("nullnull")) {
				memberVO.setProfileImg("comfuck.jpg");
			}
		} catch(NullPointerException e){
			memberVO.setProfileImg("comfuck.jpg");
		}finally{
			mMapper.updateMember(memberVO);
		}
	}

	// 회원 비밀번호 수정
	@Override
	public void updatePassword(MemberVO memberVO) {
		mMapper.updatePassword(memberVO);
	}

	// 챌린지 등급 update(plsql만들기)
	@Override
	public void updateChallGrade(MemberVO memberVO) {
		mMapper.updateChallGrade(memberVO);
	}

	// role변환(멋진활동가, 만능 재주꾼, 일반회원)
	@Override
	public void updateRole(MemberVO memberVO) { 
		mMapper.updateRole(memberVO);
	}
	
	

}
