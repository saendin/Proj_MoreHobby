package com.yedam.mohobby.service.challenge;

import lombok.Data;

@Data
//상금 분배 기준
public class ChallRewardStandardVO {
    private int priceStd; // 분배 금액 기준
    private int minPrice; // 최소값
    private int maxPrice; // 최대값
}
