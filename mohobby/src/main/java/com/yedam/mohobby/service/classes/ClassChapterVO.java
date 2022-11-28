package com.yedam.mohobby.service.classes;

import java.util.List;

import lombok.Data;

@Data
public class ClassChapterVO {
    private int chapId; //챕터id
    private int classId; //강의id
    private String chapName; //챕터명
    private String memberId; //유저id
    private List<ClassCurriculumVO> currList;  //커리큘럼
}
