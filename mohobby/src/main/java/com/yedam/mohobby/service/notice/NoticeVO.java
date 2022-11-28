package com.yedam.mohobby.service.notice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeVO {
	private String memberId;
	private String Avatar;
	private String Title;
	private String Subtitle;
	private int postId;
	private int moimId;
	private int boardType;
	private int contentType; //0: 댓글 1:좋아요
	private int noticeType;//0:sns, 1:소모임, 2:강의
	private int noticeId;
	private int nonReadCount; //안읽은 메세지수
}
