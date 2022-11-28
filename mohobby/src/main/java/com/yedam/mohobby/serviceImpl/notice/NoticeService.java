package com.yedam.mohobby.serviceImpl.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.mohobby.mapper.chat.NoticeMapper;
import com.yedam.mohobby.service.notice.NoticeVO;

@Service
public class NoticeService implements com.yedam.mohobby.service.notice.NoticeService {

	@Autowired
	NoticeMapper nMapper;
	
	//알림 전체조회
	@Override
	public List<NoticeVO> getAllNotice(String memberId) {
		return nMapper.getAllNotice(memberId);
	}
	
	//알림 등록
	@Override
	public int insertNotice(NoticeVO notice) {
		return nMapper.insertNotice(notice);
	}
	
	//알림 삭제
	@Override
	public int delectNotice(int noticeId) {
		return nMapper.delectNotice(noticeId);
	}
	//알림 전체 삭제
	@Override
	public int delectAllNotice(String memberId) {
		return nMapper.delectAllNotice(memberId);
	}
	//알림번호 조회
	@Override
	public int getNoticeId() {
		return nMapper.getNoticeId();
	}

	//안읽은 메세지수 조회
	@Override
	public int getNonReadAllChat(String memberId) {
		return nMapper.getNonReadAllChat(memberId);
	}

	//메신저 알림 전체 삭제
	@Override
	public int delectAllMsgNotice(String memberId) {
		return nMapper.delectAllMsgNotice(memberId);
	}

	@Override
	public int delectMsgNotice(int postId) {
		return nMapper.delectMsgNotice(postId);
	}

}
