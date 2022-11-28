package com.yedam.mohobby.service.openbanking;

import lombok.Data;

@Data
public class ResponseHeaderVO {
	private String trtm;
	private String rsms;
	private String apiNm;
	private String isTuno;
	private String tsymd;
	private String fintechApsno;
	private String iscd;
	private String rpcd;
	private String apiSvcCd;
}
