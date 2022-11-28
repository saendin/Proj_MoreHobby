package com.yedam.mohobby.service.moim;

import java.util.Date;

import lombok.Data;

@Data
public class MoimBoardVO {
	private int boardId; //게시글번호
	private String memberId; //회원아이디
	private String title; //제목
	private String content; //내용
	private int boardType; //게시글 분류
	private Date writeDate; //등록일자
	private int moimId; //소모임 아이디
	private int cnt; //댓글 갯수
}
