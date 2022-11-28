package com.yedam.mohobby.service.classes;

import lombok.Data;

@Data
public class ClassCurriculumVO {
    private int currId; //커리큘럼id
    private int chapId; //챕터id
    private int partNo; //회차번호
    private String partName; //회차제목
    private String videoUrl; //영상 파일 경로
    private int videoLength; //영상 길이
    private String fileUrl; //교안 파일 경로
    private String fileExtension; //파일 확장자
    private int viewProgress;	//커리큘럼 진행율
    private int classId;	//강의id
    private String chapName;
    private String classDate;
}
