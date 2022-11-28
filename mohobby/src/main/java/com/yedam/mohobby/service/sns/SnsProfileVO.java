package com.yedam.mohobby.service.sns;

import lombok.Data;

@Data
public class SnsProfileVO {
    private String memberId;
    private String nickname;
    private String profileImg;
    private String intro;
    private int role;
    private int postCnt;
    private int followerCnt;
    private int followingCnt;
}
