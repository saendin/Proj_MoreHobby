package com.yedam.mohobby.service.chat;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
public List<RoomVO>getChatRoom(String memberId);
public List<RoomVO>getChatMoimRoom(String memberId);
public List<ChatVO>getChat(String roomNo);
public List<String> getTargetId(ChatUserVO chatUser);

public List<NonReadChatVO>getNonReadChat(ChatUserVO chatUser);
public int insertMessage(ChatVO2 chat);
public int updateCheckTime(ChatUserVO chatUser);

//sns 채팅방 생성및 조회
public void getSnsChatRoomNo(CreateRoomVO createRoom);
//moim 채팅방 생성및 조회
public void getMoimChatRoomNo(CreateRoomVO createRoom);
//체크인
public int updateCheckIn(int roomId,String memberId);
//체크아웃
public int updateCheckOut(int roomId,String memberId);
//체크인아웃
public int updateCheckInOut(int preRoomId,int currentRoomId,String memberId);
//체크인 여부 조회
public int getCheckIn(int roomId, String memberId);
//안읽은 전체 메세지수
public int checkMoimId(int roomNo);
}

