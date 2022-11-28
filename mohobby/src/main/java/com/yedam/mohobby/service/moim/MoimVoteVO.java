package com.yedam.mohobby.service.moim;

import java.util.Date;

import lombok.Data;

@Data
public class MoimVoteVO {
	private int voteId; //투표번호
	private String memberId; //회원아이디
	private Date startDate; //시작일
	private Date endDate; //종료일
	private String topic; //투표주제
}
