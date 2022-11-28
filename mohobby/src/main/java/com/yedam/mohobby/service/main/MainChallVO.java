package com.yedam.mohobby.service.main;

import lombok.Data;

@Data
public class MainChallVO {
	private int challId; //챌린지 ID
	private String keywordName; // 키워드 이름
	private String challName; //챌린지명
	private int ptpId; // 참가ID
	private String memberId; // 회원아이디
	private String certImgUrl; // 인증사진
	private String certInfo; // 인증내용
}
