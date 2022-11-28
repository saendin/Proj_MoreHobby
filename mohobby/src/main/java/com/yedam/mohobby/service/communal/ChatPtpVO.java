package com.yedam.mohobby.service.communal;

import lombok.Data;

@Data
//채팅 참여자
public class ChatPtpVO {
    private int chatId; //채팅룸 ID
    private String memberId; //회원아이디
    private String entDate; //입장일자
    private int msgNo; //마지막 읽은 메세지 번호
    private int msgNoti; //읽지 않은 메세지 수
}
