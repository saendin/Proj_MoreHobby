package com.yedam.mohobby.service.challenge;

import java.util.Date;

import lombok.Data;

@Data
//챌린저스 가입 유저
public class ChallMemberVO {
  private int ptpId; // 참가ID
  private int challId; // 챌린지ID
  private String memberId; // 회원아이디
  private int ptpFee; // 등록한 상금
  private int certCnt; // 인증횟수_회원
  private String bankCode; //은행코드
  private String account; // 계좌번호
  private Date receiveDate; // 상금입금일자
  private int clearYn; //성공여부
  private int receivePrice; // 수령금액
  private int profileYn; // 프로필공개여부
  private int challRate; // 달성율
  private int accountHolder; // 예금주
  private int payStatus; // 정산상태, 0:정산전 1:정산중 2:정산완료
}
