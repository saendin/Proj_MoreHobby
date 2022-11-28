package com.yedam.mohobby.service.classes;

import java.util.Date;

import lombok.Data;

@Data
public class ClassesVO {
	private int classId; // 강의id
	private String keywordId; // 키워드아이디
	private String regionId; // 지역번호
	private String memberId; // 회원아이디
	private String className; // 강의명
	private int classPrice; // 수강료
	private String classInfo; // 상세설명 html 파일 경로
	private int imgAmount; // 이미지 등록개수
	private int classType; // 강의 유형 0:온라인, 1:오프라인
	private int certAble; // 수료증 발급 가능여부 0:발급불가 1:발급가능
	private int certStandard; // 수료증 발급 기준
	private String account_holder;  //예금주
	private String bankCode; // 은행코드
	private String account; // 계좌번호
	private String postcode; // 은행코드 011:농협, 045:새마을, 088:신한, 020:우리, 카카오뱅크:090, 기업:003, 부산:032, 국민:004,
								// 대구:031, 우체국:071, K뱅크:089
	private String address;   //주소
	private String addressDetail; // 상세주소
	private String startDate; // 시작일자
	private String endDate; // 종료일자
	private int auditStatus; // 승인상태 0:심사대기, 1:심사중, 2:승인, 3:미승인
	private String auditReturn; // 반려사유
	private int profileYn; //0: 공개 1:비공개
	private int jjim;  //0:찜X 1:찜O
	private String nickname;  //강사 닉네임
	private String keywordName; //카테고리 이름
	private int jjimTotal;  //찜 토탈
	private int reviewTotal;  //후기 토탈
	private int qnaTotal;  //qna 토탈
	private String jobName;  //직업
	private int chapTotal;  //챕터 토탈
	private int currTotal; //커리큘럼 토탈
	private int totalViewProgress;	//진행율
	private int lastCurrId;		//이어보기 할 커리큘럼 아이디
	private String certyn;	//수료증 발급 여부
	private int weekTimes;	//주 몇회 수업인지
	private int weeks;	//몇 주 수업인지
	private int startTime;
	private int endTime;
	private String needs;
	private int reviewyn;
}
