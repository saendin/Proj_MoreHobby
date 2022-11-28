package com.yedam.mohobby.service.user;

import lombok.Data;

@Data
public class MypageMoimVO {
	private String memberId; //회원아이디
	private int moimId; //소모임아이디
	private int memberRole; //권한 0:일반회원 1:관리자
	private String moimName; //소모임 명
	private String moimInfo; //소모임 소개글
	private String moimCatg; //소모임 카테고리
	private String moimRegion; //소모임 지역
	private String keywordName; // 키워드 이름
	private int regCnt; // 참여인원 
}
