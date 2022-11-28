package com.yedam.mohobby.web.notice;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.notice.NoticeService;
import com.yedam.mohobby.service.notice.NoticeVO;

@RestController
@CrossOrigin(origins = "*")
public class NoticeController {

	@Autowired
	NoticeService nService;
	
	// 알림 전체 삭제
	@GetMapping("/getAllNotice")
	public @ResponseBody List<NoticeVO> AllNotice(@RequestParam String memberId) {
		return nService.getAllNotice(memberId);
	}
	
	// 알림 삭제
	@DeleteMapping("/deleteNotice")
	public int deleteNotice(@RequestParam int noticeId) {
		return nService.delectNotice(noticeId);
	}
	
	// 알림 전체 삭제
	@DeleteMapping("/deleteAllNotice")
	public int deleteNotice(@RequestParam String memberId) {
		return nService.delectAllNotice(memberId);
	}
	// 메신저 알림 전체 삭제
	@DeleteMapping("/deleteAllMsgNotice")
	public int deleteMsgAllNotice(@RequestParam String memberId) {
		return nService.delectAllMsgNotice(memberId);
	}
	// 메신저 알림 단건 삭제
	@DeleteMapping("/deleteMsgNotice")
	public int deleteMsgNotice(@RequestParam int postId) {
		System.out.println(postId);
		return nService.delectMsgNotice(postId);
	}

}
