package com.yedam.mohobby.service.iamport;

import java.net.MalformedURLException;

import org.springframework.stereotype.Service;

@Service
public interface IamportService {
	public GetCertInfoResponseVO requestCertification(String imp_uid) throws MalformedURLException;
	public String getToken() throws MalformedURLException;
}
