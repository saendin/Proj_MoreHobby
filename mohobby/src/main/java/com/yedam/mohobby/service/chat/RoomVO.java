package com.yedam.mohobby.service.chat;

import com.yedam.mohobby.service.communal.AES256Util;

public class RoomVO {
	public String nickName;
	private int roomNo;
	private String profileImg;
	private String content;
	AES256Util aes = new AES256Util();
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public String getContent() throws Exception {
		return(aes.decrypt(content));
		//return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getNonReadChat() {
		return nonReadChat;
	}
	public void setNonReadChat(String nonReadChat) {
		this.nonReadChat = nonReadChat;
	}
	private String msgTime;
	private String checkIn;
	private String nonReadChat;
	
}
