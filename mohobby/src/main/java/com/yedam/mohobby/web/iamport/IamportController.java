package com.yedam.mohobby.web.iamport;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.iamport.GetCertInfoResponseVO;
import com.yedam.mohobby.service.iamport.IamportService;

/**
 * 
 * @author 최은경
 * 아임포트 결제, 본인인증
 *
 */
@RestController
@RequestMapping("/iamport")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
public class IamportController {
	
	@Autowired
	IamportService iamService;
	
	@GetMapping("/cert/{imp_uid}")
	public GetCertInfoResponseVO requestCertification
			(@PathVariable("imp_uid") String imp_uid) 
					throws MalformedURLException {
		return iamService.requestCertification(imp_uid);
	}
}
