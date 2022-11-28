package com.yedam.mohobby.service.communal;

import lombok.Data;

@Data
//해시태그
public class HashtagVO {
    private String hashtag; //해시태그
    private int targetId; //타겟아이디
    private int targetType; //타겟유형
    private int useCount; //사용횟수
}
