package com.yedam.mohobby.service.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatNoticeVO {
	private String content;
	private String memberId;
	private String roomNo;
}
