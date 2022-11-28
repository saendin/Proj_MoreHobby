package com.yedam.mohobby.service.sns;

import lombok.Data;

@Data
public class SnsFollowVO {
	private int followId; //팔로우번호
	private String followingId; //팔로우당한사람
	private String followerId; //팔로우 건사람
	private String profileImg; //프로필이미지
    private String intro; //회원설명
    private String nickname; //닉네임
}
