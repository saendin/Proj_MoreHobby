package com.yedam.mohobby.service.moim;

import lombok.Data;

@Data
public class MoimVO {
	private int moimId; //소모임id
	private String memberId;
	private String moimImg; //소모임사진
	private String moimName; //소모임 명
	private String moimInfo; //소모임 소개글
	private String moimCatg; //소모임 카테고리
	private String moimRegion; //소모임 지역
	private int regCnt; //가입 인원
	private int moimOpen; //소모임 공개여부
	private int maxAge; //가입 최대나이
	private int minAge; //가입 최소나이
	private int gender; //성별
	private int maxPeople; //가입 최대인원
	private int moimMemberId; //인조식별키
}
