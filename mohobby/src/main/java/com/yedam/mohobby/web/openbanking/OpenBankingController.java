package com.yedam.mohobby.web.openbanking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yedam.mohobby.service.openbanking.BankRealNameRequestVO;
import com.yedam.mohobby.service.openbanking.BankRealNameResponseVO;
import com.yedam.mohobby.service.openbanking.RequestHeaderVO;
import com.yedam.mohobby.service.openbanking.ResponseHeaderVO;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class OpenBankingController {
 
	@GetMapping(value = "/bankRealName")
	public @ResponseBody BankRealNameResponseVO accountNumber(
			@RequestParam String Bncd,
			@RequestParam String Acno) 
			throws MalformedURLException 
	{
		
		String response = "";
		BankRealNameResponseVO respVO = new BankRealNameResponseVO();
		
		URL url;
		StringBuilder sb = new StringBuilder();
		OutputStream os;
		BufferedReader br;
		Gson gson = new Gson();
		
		
		//현재 날짜 구하기
		LocalDateTime date = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		
		//헤더 추가 정보
		RequestHeaderVO header = new RequestHeaderVO();
		if(Bncd.equals("011")) {
			//농협계좌
			url = new URL("https://developers.nonghyup.com/InquireDepositorAccountNumber.nh");
			header.setApiNm("InquireDepositorAccountNumber");
		} else {
			//타행계좌
			url = new URL("https://developers.nonghyup.com/InquireDepositorOtherBank.nh");
			header.setApiNm("InquireDepositorOtherBank");
		}
		header.setTsymd(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
		header.setIsTuno(date.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
		
		//리퀘스트 바디
		BankRealNameRequestVO requestBody = new BankRealNameRequestVO();
		requestBody.setHeader(header);
		requestBody.setAcno(Acno);
		requestBody.setBncd(Bncd);
		
		//리퀘스트 바디 -> json
		String bodyJson = gson.toJson(requestBody);
		System.out.println(bodyJson);
		
		
		try { 
		
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
		  
			//Request Header 정의 
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
		  
			//전송방식 
			con.setRequestMethod("POST");
		  
			con.setConnectTimeout(5000); // 연결 타임아웃 설정(5초) 
			con.setReadTimeout(5000); //읽기 타임아웃 설정(5초) 
			con.setDoOutput(true); // URL 연결을 출력용으로 사용(true)
			
			
			os = con.getOutputStream();
			os.write(bodyJson.toString().getBytes("utf-8"));
			os.close();
			
			
			System.out.println("getContentType():" + con.getContentType());
			System.out.println("getResponseCode():" + con.getResponseCode());
			System.out.println("getResponseMessage():" + con.getResponseMessage());
			
			
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8")); 
				String line = null; 
				while((line = br.readLine()) != null) { 
					sb.append(line + "\n"); 
				}
				br.close();
				
				response = ""+sb.toString();
				System.out.println(response);
				
				//json 객체
				@SuppressWarnings("deprecation")
				JsonObject respJson = new JsonParser().parse(response).getAsJsonObject();
				
				//response 객체
				if(respJson.has("Bncd")) {
					respVO.setBncd(respJson.get("Bncd").getAsString());
				}
				if(respJson.has("Dpnm")) {
					respVO.setDpnm(respJson.get("Dpnm").getAsString());
				}
				if(respJson.has("Acno")) {
					respVO.setAcno(respJson.get("Acno").getAsString());
				}
				ResponseHeaderVO respHeader = new ResponseHeaderVO();
				JsonObject respHeaderJson = (JsonObject) respJson.get("Header");
				respHeader.setTrtm(respHeaderJson.get("Trtm").getAsString());
				respHeader.setRsms(respHeaderJson.get("Rsms").getAsString());
				respHeader.setApiNm(respHeaderJson.get("ApiNm").getAsString());
				respHeader.setIsTuno(respHeaderJson.get("IsTuno").getAsString());
				respHeader.setTsymd(respHeaderJson.get("Tsymd").getAsString());
				respHeader.setFintechApsno(respHeaderJson.get("FintechApsno").getAsString());
				respHeader.setIscd(respHeaderJson.get("Iscd").getAsString());
				respHeader.setRpcd(respHeaderJson.get("Rpcd").getAsString());
				respHeader.setApiSvcCd(respHeaderJson.get("ApiSvcCd").getAsString());
				respVO.setHeader(respHeader);
				
		    }
		  
		  } catch(Exception e) {
			  e.printStackTrace();
		  }
		 
		
		System.out.println(respVO);
		return respVO;
	}
}