package com.yedam.mohobby.service.notice;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface NoticeService {
	public List<NoticeVO> getAllNotice(String memberId);
	public int insertNotice(NoticeVO notice);
	public int delectNotice(int noticeId);
	public int delectMsgNotice(int postId);
	public int delectAllNotice(String memberId);
	public int delectAllMsgNotice(String memberId);
	public int getNoticeId();
	public int getNonReadAllChat(String memberId);
}
