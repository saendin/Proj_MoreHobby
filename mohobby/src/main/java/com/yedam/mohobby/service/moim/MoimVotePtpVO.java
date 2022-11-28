package com.yedam.mohobby.service.moim;

import lombok.Data;

@Data
public class MoimVotePtpVO {
	private int itemId; //투표항목번호
	private int voteId; //투표번호
	private String memberId; //회원아이디
}
