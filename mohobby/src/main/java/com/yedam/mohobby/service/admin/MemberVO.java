package com.yedam.mohobby.service.admin;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class MemberVO {
	private String memberId; //멤버아이디
	private String regionId; //지역아이디
	private String nickName; //닉네임
	private String password; //비밀번호
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date birth; // 생년월일
	private String email; //이메일
	private String phoneNum; //휴대폰번호
	private String gender; //성별
	private int role; //롤
	private String profileImg; //프로필이미지
	private String intro; //회원설명
	private String tokenType; //토큰유형
	private String token; //토큰
	private int challGrade; //챌린지등급
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date regDate; //가입일자
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date delDate; //탈퇴일자
	private String memberName;
}
