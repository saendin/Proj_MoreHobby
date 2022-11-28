package com.yedam.mohobby.service.classes;

import java.util.Date;

import lombok.Data;

@Data
public class ClassCertificateVO {
    private int certId; //발급id
    private String memberId; //회원아이디
    private int classId; //강의id
    private int rate; //달성율
    private Date certDate; //발급일자
}
