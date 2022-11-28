package com.yedam.mohobby.service.chat;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentVO {
	private int roomNo;
	private String content;
	private String memberId;
	private List<String> memberIds;
	private String hour;
	private String nickname;
	private String profileImg;
}
