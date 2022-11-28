package com.yedam.mohobby.service.moim;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MoimVoteListVO {
	private int itemId; //투표항목번호
	private String content; //투표항목내용
	private int cnt; //투표인원
	private int voteId; //투표번호
	private String memberId; //회원아이디
	private Date startDate; //시작일
	private Date endDate; //종료일
	private String topic; //투표주제
	private int moimId; // 소모임 아이디
	private int commentCnt; //댓글수
	private String voteUser; //투표자
	private int itemSelect; //아이템 선택
	private int preSelect; //선택된 아이템
	private List<MoimVoteItemVO> votelist;
}
