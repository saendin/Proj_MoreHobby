package com.yedam.mohobby.service.classes;

import lombok.Data;

@Data
public class ClassBoardVO {
    private int boardId; //게시글번호
    private String memberId; //회원아이디
    private int classId; //강의id
    private int boardType; //글 유형
    private String title; //글 제목
    private String content; //글 내용
    private int rate; //점수
    private int replyCheck; //답변처리
    private String writeDate; //작성시간
    private String profileImg;  //프로필이미지
    private String nickname;  //닉네임
    private int secret; //0:공개, 1:비공개
    private int commentTotal;   //코멘트 토탈
    private String className; // 강의 명 
    private int classType; // 0온라인 1오프라인 
}
