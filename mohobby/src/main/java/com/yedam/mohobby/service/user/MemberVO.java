package com.yedam.mohobby.service.user;

import java.sql.Date;


import lombok.Data;
@Data
public class MemberVO {
	private String memberId; //멤버아이디
	private String regionId; //지역아이디
	private String nickName; //닉네임
	private String password; //비밀번호
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
	private Date regDate; //가입일자
	private Date delDate; //탈퇴일자
	private String memberName;
}
