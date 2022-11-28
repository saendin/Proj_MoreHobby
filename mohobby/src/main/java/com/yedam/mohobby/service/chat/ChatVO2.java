package com.yedam.mohobby.service.chat;

import com.yedam.mohobby.service.communal.AES256Util;

public class ChatVO2 {
	AES256Util aes = new AES256Util();
	public String roomNo;
	private String content;
	private String memberId;
	private String minute;
	private String hour;
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getContent() throws Exception {
		return content;
	}
	public void setContent(String content) throws Exception {
		this.content = content;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMinute() {
		return minute;
	}
	public void setMinute(String minute) {
		this.minute = minute;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
}
