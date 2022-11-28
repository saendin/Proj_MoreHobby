package com.yedam.mohobby.service.main;

import java.util.Date;

import lombok.Data;

@Data
public class MainClassVO {
	private int classId; // 강의id
	private String keywordName; // 키워드 이름
	private String className; // 강의명
	private Date startDate; // 시작일자
	private int classPrice; // 수강료
	private String memberId;
	private String regionName;
}
