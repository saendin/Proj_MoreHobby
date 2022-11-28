package com.yedam.mohobby.service.moim;

import lombok.Data;

@Data
public class MoimMemberVO {
	private String memberId; //회원아이디
	private int moimId; //소모임아이디
	private int memberRole; //권한 0:일반회원 1:관리자
	private int profileYn; //0: 공개 1:비공개
	private int moimMemberId; //인조식별키
	private String moimName; //모임이름
	
}
