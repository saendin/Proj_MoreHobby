package com.yedam.mohobby.service.classes;

import lombok.Data;

@Data
public class ClassPayVO {
    private int payId; //주문id
    private String status;
    private String pg_type;
    private String pg_tid;
    private String pg_provider;
    private String pay_method;
    private int paid_at;
    private int paid_amount;
    private String name;
    private String merchant_uid;
    private String imp_uid;
    private String custom_data;
    private String currency;
    private int card_quota;
    private String card_number;
    private String card_name;
    private String buyer_tel;
    private String buyer_postcode;
    private String buyer_name;
    private String buyer_email;
    private String buyer_addr;
    private String buyer_addr_detail;
    private String bank_name;
    private String apply_num;
    private String vbank_date;
    private String vbank_holder;
    private String vbank_name;
    private String vbank_num;
    private String memberId; //회원아이디
    private int classId; //강의 아이디
    private int dlvyFee; //배송비
    private String dlvyMemo; //요청사항
    private int classPrice; //수강료
    private int needsPrice; //준비물금액
    private int point;  //사용한포인트
    private int classStatus;  //0:수강중, 1:수강완료
}
