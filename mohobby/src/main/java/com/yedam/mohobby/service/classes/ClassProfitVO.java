package com.yedam.mohobby.service.classes;

import java.util.Date;

import lombok.Data;

@Data
public class ClassProfitVO {
    private int profitId; //수익id
    private int classId; //강의id
    private int price; //누적금액
    private int withdrawable; //출금가능금액
    private Date calcDate; //산정기준일자
    private int withdrawCheck; //출금여부
    private String accountHolder;
    private String bankCode;
    private String account;
}
