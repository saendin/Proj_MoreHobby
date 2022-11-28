package com.yedam.mohobby.service.openbanking;

import lombok.Data;

@Data
public class BankRealNameRequestVO {
	private RequestHeaderVO Header;
	private String Bncd;
	private String Acno;
}
