package com.yedam.mohobby.service.sns;

import java.util.Date;

import lombok.Data;

@Data
public class SnsPostVO {
private int postId; //게시글번호
private String memberId; //유저아이디
private String profileImg; //프로필 이미지
private String hashtag; //해시태그
private String content; //내용
private Date writeDate; //피드등록일
private String thumbnail; //대표이미지
private int likes; //좋아요 수
private int cmts; //댓글 수
private int imgs; //이미지 수
private int secPost; //비밀게시글 여부
//private int secUser; //비밀유저 여부 --> user로
}