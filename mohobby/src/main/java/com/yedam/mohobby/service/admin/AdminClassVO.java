package com.yedam.mohobby.service.admin;

import java.util.Date;

import lombok.Data;

@Data
public class AdminClassVO {

	private int classId; // 강의id
	private String memberId; // 회원아이디
	private String className; // 강의명
	private String regionName; // 키워드 이름
	private String categoryName; // 키워드 이름
	private int classPrice; // 수강료
	private int classType; // 강의 유형 0:온라인, 1:오프라인
	private Date startDate; // 시작일자
	private Date endDate; // 종료일자
	private int auditStatus; // 승인상태 0:심사대기, 1:심사중, 2:승인, 3:미승인
	private String auditReturn; // 반려사유

}


