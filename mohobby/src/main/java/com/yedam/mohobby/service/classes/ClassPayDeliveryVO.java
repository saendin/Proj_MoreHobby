package com.yedam.mohobby.service.classes;

import lombok.Data;

@Data
public class ClassPayDeliveryVO {
    private int dlvyId; //배송id
    private int payId; //주문id
    private int prodId; //상품id
    private int prodAmount; //상품수량
    private String dlvyNo; //배송번호
    private String dlvyCorp; //택배사
    private int dlvyStatus; //진행상태
}
