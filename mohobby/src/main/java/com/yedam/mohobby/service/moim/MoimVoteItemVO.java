package com.yedam.mohobby.service.moim;

import lombok.Data;

@Data
public class MoimVoteItemVO {
	private int itemID; //투표항목번호
	private int voteId; //투표번호
	private String content; //투표항목내용
	private int cnt; //투표인원
}
