package com.yedam.mohobby.service.user;

import java.util.Date;

import lombok.Data;

@Data
public class MypageChallVO {
	private String challName; //챌린지명
	private Date startDate; // 시작일자
    private Date endDate; // 종료일자
    private int challPtp; //참여인원수
    private String imgUrl; //사진주소
    private int totalReward; // 총 상금
    private int challRate; // 달성율
    private String success; // 성공
}
