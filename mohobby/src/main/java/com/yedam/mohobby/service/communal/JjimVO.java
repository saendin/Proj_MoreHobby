package com.yedam.mohobby.service.communal;

import lombok.Data;

@Data
//찜
public class JjimVO {
    private int targetId; //타겟아이디 : 강의id, sns id, 댓글id
    private String memberId; //회원 아이디
    private int targetType; //타겟 유형
}
