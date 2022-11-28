package com.yedam.mohobby.service.moim;

import java.util.List;

import lombok.Data;

@Data
public class MoimDutchPtpVO {
	private int dutchId; //n빵id
	private List<String> memberId; //회원아이디
	private int calcPrice; //정산금액
	private int calcCheck; //정산여부
	private int moimId; //모임아이디
	private String moneyTarget; //정산할 멤버들
}
