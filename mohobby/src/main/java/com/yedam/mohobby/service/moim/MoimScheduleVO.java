package com.yedam.mohobby.service.moim;

import lombok.Data;

@Data
public class MoimScheduleVO {
	private int skedId; //일정 아이디
	private String memberId; //회원아이디
	private String title; //일정제목
	private String info; //일정설명
	private String startTime; //일정 시작시간
	private String endTime; //일정 종료시간
	private String startDate; //시작일자
	private String endDate; //종료일자
	private int moimId;
}
