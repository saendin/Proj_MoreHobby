package com.yedam.mohobby.service.chat;

import lombok.Data;

@Data
public class NonReadChatVO {
	private String roomNo;
	private int nonReadCount;
}
