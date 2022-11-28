package com.yedam.mohobby.service.classes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ClassNeedsVO {
    private int prodId; //상품id
    private int classId; //강의id
    private int prodPrice; //상품 가격
    private String prodName; //상품명
    private String prodImg; //상품 이미지
}
