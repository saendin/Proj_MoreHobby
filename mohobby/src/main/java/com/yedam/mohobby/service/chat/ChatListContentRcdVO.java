package com.yedam.mohobby.service.chat;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatListContentRcdVO {

	private List<String> memberId;
	private String content;
	private String roomNo;
	private String msgTime;
}
