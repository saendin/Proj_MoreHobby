package com.yedam.mohobby.service.admin;

import java.util.Date;

import lombok.Data;

@Data
public class AdminChallVO {
	
	private int challId; // 챌린지ID
	private String keywordName; // 키워드 이름
	private int dayCert; // 하루 인증 횟수
	private String challName; //챌린지명
    private Date startDate; // 시작일자
    private Date endDate; // 종료일자
    private int challPtp; //참여인원수
    private int reward; // 추가상금
    private int sumFee; // 누적상금
    private int payStatus; // 정산상태 0:정산전 1:정산중 2:정산완료
}
