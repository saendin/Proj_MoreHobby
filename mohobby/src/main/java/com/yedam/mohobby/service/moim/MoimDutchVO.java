package com.yedam.mohobby.service.moim;

import java.util.Date;

import lombok.Data;

@Data
public class MoimDutchVO {
	private int dutchId; //n빵아이디
	private int moimId; //소모임 아이디
	private String memberId; //회원아이디 - 주최자
	private int totalPrice; //총 금액
	private String title; //n빵제목	
	private int people; //참여인원
	private int finish; //완료 여부 0:진행중 1:완료
	private Date writeDate; //작성 날짜
	private String moneyTarget; // n빵할 대상
	private int calcPrice; // n빵된 돈
	private int calcCheck; // 확인 여부
	private String profileImg; // 멤버 프로필 이미지 
}
