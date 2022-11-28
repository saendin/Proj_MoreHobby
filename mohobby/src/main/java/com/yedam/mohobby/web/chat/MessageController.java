package com.yedam.mohobby.web.chat;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.beust.jcommander.internal.Console;
import com.yedam.mohobby.service.chat.ChatListContentResVO;
import com.yedam.mohobby.service.chat.ChatService;
import com.yedam.mohobby.service.chat.ContentVO;
import com.yedam.mohobby.service.notice.NoticeService;
import com.yedam.mohobby.service.notice.NoticeVO;
import com.yedam.mohobby.service.notice.ResNoticeVO;
import com.yedam.mohobby.service.user.MemberService;
import com.yedam.mohobby.service.moim.MoimService;

@Controller
public class MessageController {

	@Autowired
	MemberService mService;

	@Autowired
	NoticeService nService;

	@Autowired
	MoimService moService;

	@Autowired
	ChatService cService;

	@Autowired
	SimpMessageSendingOperations sendTemplate;

	@MessageMapping("/getSubscribeInfo")
	public void getSubscribeInfo(String RoomNo) {
		sendTemplate.convertAndSend("/topic/room/" + RoomNo, RoomNo);
	}

	@MessageMapping("/send")
	public void send(ContentVO content) {
		NoticeVO noticeVO = new NoticeVO();
		ResNoticeVO resNotice = new ResNoticeVO();
		content.setNickname(mService.getMember(content.getMemberId()).getNickName());
		content.setProfileImg(mService.getMember(content.getMemberId()).getProfileImg());
		System.out.println(content.getNickname());
		sendTemplate.convertAndSend("/topic/room/" + content.getRoomNo(), content);
		// 실시간 메시지 보내기

		ChatListContentResVO res = new ChatListContentResVO();
		for (int i = 0; i < content.getMemberIds().size(); i++) {

		
			sendTemplate.convertAndSend("/queue/" + content.getMemberIds().get(i), content);

			if (!content.getMemberId().equals(content.getMemberIds().get(i))) {
				// 상대방이 같은방에 없을때는 알림을 보낸다.

				if (cService.getCheckIn(content.getRoomNo(), content.getMemberIds().get(i)) == 0) {
					resNotice.setNoticeId(nService.getNoticeId());
					resNotice.setNickname(mService.getMember(content.getMemberId()).getNickName());
					resNotice.setNoticeType(2);
					resNotice.setTargetId(content.getMemberIds().get(i));
					resNotice.setMyId(content.getMemberId());
					resNotice.setPostId(content.getRoomNo());
					if (cService.checkMoimId(content.getRoomNo()) == 0) {
						resNotice.setContentType(0);
						resNotice.setProfileImge(mService.getMember(content.getMemberIds().get(i)).getProfileImg());
					} else {
						resNotice.setContentType(1);
						resNotice.setProfileImge(moService.getMoimInfo(content.getRoomNo()).getMoimImg());
					}
					// db에 담을 정보
					noticeVO.setMemberId(content.getMemberIds().get(i));
					noticeVO.setAvatar(resNotice.getProfileImge());
					noticeVO.setTitle(resNotice.getNickname() + "님으로 부터");
					noticeVO.setSubtitle("새로운 메세지가 도착했습니다.");
					noticeVO.setPostId(content.getRoomNo());
					noticeVO.setNoticeType(2);
					System.out.println("get memeber Id :  " + content.getMemberIds().get(i));

					sendTemplate.convertAndSend("/queue/" + content.getMemberIds().get(i) + "/notice", resNotice);
					nService.insertNotice(noticeVO);

				}
			}
		}
	}

	@MessageMapping("/getSubscribeId")
	public void getSubscribeId(String RoomNo) {
		sendTemplate.convertAndSend("/topic/room/" + RoomNo, RoomNo);
		
		sendTemplate.convertAndSend("/queue/" + RoomNo + "/notice" ,"chatNotice");
	}

	@MessageMapping("/SubscribeId")
	public void SubscribeId(String memberId) {
		System.out.println(memberId);
		sendTemplate.convertAndSend("/queue/" + memberId + "/notice", memberId);
	}

	@MessageMapping("/SubscribeIds")
	public void SubscribeIds(String memberId) {
		System.out.println("memberId : " + memberId);
		sendTemplate.convertAndSend("/queue/" + memberId, memberId);
	}

	// 알림
	@MessageMapping("Notice")
	public void NoticeSns(ResNoticeVO resNotice) {
		NoticeVO noticeVO = new NoticeVO();
		if (!resNotice.getMyId().equals(resNotice.getTargetId())) {
			System.out.println("문자열비교성공");
			System.out.println(resNotice.getMyId());
			System.out.println(resNotice.getTargetId());
			if (resNotice.getMyId() != resNotice.getTargetId()) {
				resNotice.setNoticeId(nService.getNoticeId());
				noticeVO.setBoardType(resNotice.getBoardType());
				noticeVO.setMoimId(resNotice.getMoimId());
				noticeVO.setPostId(resNotice.getPostId());
				noticeVO.setNoticeType(resNotice.getNoticeType());
				noticeVO.setContentType(resNotice.getContentType());
				// sns 알림
				if (resNotice.getNoticeType() == 0) {
					resNotice.setProfileImge(mService.getMember(resNotice.getMyId()).getProfileImg());
					resNotice.setNickname(mService.getMember(resNotice.getMyId()).getNickName());
					// db에 담을정보
					noticeVO.setMemberId(resNotice.getTargetId());
					noticeVO.setAvatar(resNotice.getProfileImge());
					noticeVO.setTitle(resNotice.getNickname());
					// sns - 좋아요 클릭시
					if (resNotice.getContentType() == 0) {
						if (resNotice.getLikeStatus() == 0) {
							noticeVO.setSubtitle("좋아요를 눌렀습니다.");
						} else if (resNotice.getLikeStatus() == 1) {
							noticeVO.setSubtitle("좋아요를 취소했습니다.");
						}
						// sns - 댓글 등록시
					} else if (resNotice.getContentType() == 1) {
						noticeVO.setSubtitle("댓글을 남기셨습니다.");
					}
				}
				// 소모임 알림
				else if (resNotice.getNoticeType() == 1) {
					resNotice.setProfileImge(moService.getMoimInfo(resNotice.getMoimId()).getMoimImg());
					resNotice.setNickname(moService.getMoimInfo(resNotice.getMoimId()).getMoimName());

					// db에 담을정보
					noticeVO.setMemberId(resNotice.getTargetId());
					noticeVO.setAvatar(resNotice.getProfileImge());
					noticeVO.setTitle(resNotice.getNickname());
					// 소모임 - 댓글
					if (resNotice.getContentType() == 0) {
						noticeVO.setSubtitle("댓글을 남기셨습니다.");
					}
					// 소모임 - 게시글 등록시
					else if (resNotice.getContentType() == 1) {
						noticeVO.setSubtitle("새로운 게시글이 등록되었습니다.");
					}
				} else if (resNotice.getBoardType() == 2) {
				}
				System.out.println(noticeVO);
				sendTemplate.convertAndSend("/queue/" + resNotice.getTargetId() + "/notice", resNotice);
				nService.insertNotice(noticeVO);
			}
		}
	}
}