package com.yedam.mohobby.service.moim;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
//댓글 단건조회 VO
public class MoimCommentVO {
	
	private int commId;
	private String boardContent;
	private String boardWriter;
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date writeDate;
	private int parentCommId;
	private String content; //댓글 내용
	private String commentWriter;
	private int moimId;
	private int boardType;
	private int boardId; // (== target_id) moim_board에서 가져옴
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date commentDate;
	
}
