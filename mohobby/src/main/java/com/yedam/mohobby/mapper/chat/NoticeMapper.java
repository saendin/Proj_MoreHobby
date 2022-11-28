package com.yedam.mohobby.mapper.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.mohobby.service.notice.NoticeVO;

@Mapper
public interface NoticeMapper {
	//알림 전체 조회
	public List<NoticeVO> getAllNotice(String memberId);
	//알림 등록
	public int insertNotice(NoticeVO notice);
	//알림 단건 삭제
	public int delectNotice(@Param("noticeId")int noticeId);
	//메세지 알림 단건 삭제
	public int delectMsgNotice(@Param("postId")int postId);
	//알림 전체 삭제
	public int delectAllNotice(@Param("memberId")String memberId);
	//메세지 알림 전체 삭제
	public int delectAllMsgNotice(@Param("memberId")String memberId);
	//알림 번호 조회
	public int getNoticeId();
	//안읽은 메세지수 전체조회
	public int getNonReadAllChat(@Param("memberId")String memberId);
}
