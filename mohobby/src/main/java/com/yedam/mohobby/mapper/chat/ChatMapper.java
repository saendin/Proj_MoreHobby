package com.yedam.mohobby.mapper.chat;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.mohobby.service.chat.ChatUserVO;
import com.yedam.mohobby.service.chat.ChatVO;
import com.yedam.mohobby.service.chat.ChatVO2;
import com.yedam.mohobby.service.chat.CreateRoomVO;
import com.yedam.mohobby.service.chat.NonReadChatVO;
import com.yedam.mohobby.service.chat.RoomVO;

@Mapper
public interface ChatMapper {
public List<RoomVO>getChatRoom(String memberId);
public List<RoomVO>getChatMoimRoom(String memberId);
public List<ChatVO>getChat(String roomNo);
public List<String>getTargetId(ChatUserVO chatUser);
public int insertMessage(ChatVO2 chat);

public List<NonReadChatVO>getNonReadChat(ChatUserVO chatUser);
public int updateCheckTime(ChatUserVO chatUser);
//sns 채팅방 생성및 조회
public void getSnsChatRoomNo(CreateRoomVO createRoom);
//moim 채팅방 생성및 조회
public void getMoimChatRoomNo(CreateRoomVO createRoom);
//체크인
public int updateCheckIn(@Param("roomId") int roomId,@Param("memberId") String memberId);
//체크아웃
public int updateCheckOut(@Param("roomId") int roomId,@Param("memberId") String memberId);
//체크인아웃
public int updateCheckInOut(@Param("preRoomId") int preRoomId,@Param("currentRoomId")int currentRoomId,@Param("memberId") String memberId);
//체크인 여부 조회
public int getCheckIn(@Param("roomId")int roomId,@Param("memberId") String memberId);
//1:1 1:m 채팅 여부
public int checkMoimId(@Param("roomNo")int moimId);

}
