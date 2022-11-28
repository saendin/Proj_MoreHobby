package com.yedam.mohobby.service.chat;

import java.sql.Date;

import lombok.Data;

@Data
public class MessageVO {
	
	private String roomNo;
	private Date msgTime;
	private String content;
	private String memberId;
}
