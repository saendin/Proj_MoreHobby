package com.yedam.mohobby.service.challenge;

import java.util.Date;

import lombok.Data;

@Data
public class ChallCertVO {
    private int certPart; // 인증회차
    private int ptpId; // 챌린지 참여ID
    private String certImgUrl; // 인증사진
    private String certInfo; // 인증내용
    private Date certDate; // 인증시간
    private int certYn; // 인증성공여부
}
