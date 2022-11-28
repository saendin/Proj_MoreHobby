package com.yedam.mohobby.service.iamport;

import lombok.Data;

@Data
public class GetCertInfoResponseVO {
	private String name;
	private String birthday;
	private String imp_uid;
	private String merchant_uid;
	private String pg_provider;
	private String pg_tid;
	private String phone;
}
