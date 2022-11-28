package com.yedam.mohobby.service.openbanking;

import lombok.Data;

@Data
public class RequestHeaderVO {
	private String ApiNm;
	private String Tsymd;
	private String Trtm = "003544";
	private String Iscd = "001602"; //기관코드
	private String FintechApsno = "001";
	private String ApiSvcCd = "DrawingTransferA";
	private String IsTuno;
	private String AccessToken = "4a13e022f828e7bfe46e375a3ce0edb6889e06f5d28acec32abd461389365f96"; //토큰
}
