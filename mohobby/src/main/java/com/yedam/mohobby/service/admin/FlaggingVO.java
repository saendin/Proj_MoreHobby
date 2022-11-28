package com.yedam.mohobby.service.admin;

import lombok.Data;


@Data
public class FlaggingVO {
    private int flagId;
    private String flagFrom;
    private String flagTo;
    private String flagCode;
    private String flagReason;
    private int adminConfirm;
    private int flagResult;
    private String moimName;
}
