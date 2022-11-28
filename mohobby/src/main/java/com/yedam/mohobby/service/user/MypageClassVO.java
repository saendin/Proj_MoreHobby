package com.yedam.mohobby.service.user;

import java.util.Date;

import lombok.Data;

@Data
public class MypageClassVO {

	private String className; // 강의명
	private String keywordName; // 키워드 이름
	private int classType; // 강의 유형 0:온라인, 1:오프라인
	private String regionId; // 지역번호
	private Date startDate; // 시작일자
	private Date endDate; // 종료일자
}
