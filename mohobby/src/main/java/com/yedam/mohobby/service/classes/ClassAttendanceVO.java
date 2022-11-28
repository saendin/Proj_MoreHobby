package com.yedam.mohobby.service.classes;

import java.util.Date;

import lombok.Data;

@Data
public class ClassAttendanceVO {
	private int classId;	//클래스id
	private String className;	//클래스이름
	private String address;	//강의장소
	private Date startDate;	//개강일자
	private Date endDate;	//종강일자
	private int startTime;	//수업시작시간
	private int endTime;	//수업종료시간
    private int currId; //커리큘럼id
    private String memberId; //회원아이디
    private Date attdDate; //출석시간
    private Date outDate; //외출 시간
    private Date comebackDate; //복귀시간
    private Date leaveDate; //조퇴시간
    private Date exitDate; //퇴실시간
    private int recordType; //기록유형 0:출석, 1:조퇴, 2:외출, 3:결석
    private int lateCheck; //지각확인 0:정상, 1:지각
    private int viewProgress; //시청진행도
    private int totalViewProgress; //강의 총 진행율
    private int sumAttd;	//출석횟수
    private int sumOut;		//외출횟수
    private int sumLeave;	//조퇴횟수
    private int sumAbsent;	//결석횟수
    private int sumLate;	//지각횟수
    private int total;		//총 과정 횟수
    private int gone;		//과정 진행된 일수
    private double progress;	//오늘 기준 과정 진행율
    private Date classDate;	//수업일자
    private int count; 	//조퇴+외출이 3번 이상인지
}
