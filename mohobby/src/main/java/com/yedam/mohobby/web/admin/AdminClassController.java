package com.yedam.mohobby.web.admin;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.mohobby.service.admin.AdminClassService;
import com.yedam.mohobby.service.admin.AdminClassVO;
import com.yedam.mohobby.service.admin.ClassNeedsListVO;
import com.yedam.mohobby.service.classes.ClassBoardVO;
import com.yedam.mohobby.service.classes.ClassNeedsVO;
import com.yedam.mohobby.service.classes.ClassesVO;

/**
 * @create 2002/10/06
 * @author gabin
 * @title 관리자 강의
 */
@RestController
@CrossOrigin(origins = "*")
public class AdminClassController {
	
	@Autowired
	AdminClassService service;
	/**
	 * @title 강의목록 
	 * @param adminClassVO
	 * @return
	 */
	@GetMapping("/adminClassList")
	public List<AdminClassVO> getAuditClassList() {
		return service.getAuditClassList();
	};
	
	// 승인 신청한 강의 정보(vue에서 상세 설명 경로 가지고오기)
	
	// 신청 결과 업로드
	@PutMapping("/adminAuditClass")
	public void updateAuditStatus(@RequestBody ClassesVO classesVO) {
		System.out.println(classesVO.getClassId());
		System.out.println(classesVO.getAuditStatus());
		System.out.println(classesVO.getAuditReturn());
		service.updateAuditStatus(classesVO);
	}
	
	// 강사, 강의, 정산 정보, 지급 여부 등등
	
	// qna리스트 전체 가지고 오기
	@GetMapping("/QnAAllList")
	public List<ClassBoardVO> getAllQnAList() {
		return service.getAllQnAList();
	}
	
	// 선택한 클래스의 qna리스트 가지고 오기
	@GetMapping("/QnAList/{classId}")
	public List<ClassBoardVO> getQnAList(@PathVariable int classId) {
		return service.getQnAList(classId);
	}
	
	// qna답변 여부 업데이트
	@PutMapping("/QnAUpdate")
	public void updateQnAAnswer(@RequestBody ClassBoardVO classBoardVO) {
		service.updateQnAAnswer(classBoardVO);
	}
	
	// 강의 개설 신청 시 등록한 준비물 목록 조회
	@GetMapping("/applyNeeds/{classId}")
	public ClassesVO getApplyNeeds(@PathVariable int classId) {
		return service.getApplyNeeds(classId);
	}
	
	// 강의 수료증 발급 여부, 발급 기준 업데이트
	@PutMapping("/updateCert")
	public void updateCert(@RequestBody ClassesVO classesVO) {
		service.updateCert(classesVO);
	}
	
	// 강의 준비물 insert
	@PostMapping("/insertNeeds")
	public void insertNeeds(List<MultipartFile> prodImgList, ClassNeedsListVO classNeedsListVO) { 
		
		System.out.println(classNeedsListVO.getList());
		MultipartFile prodImg = null;
		for(int i = 0; i < prodImgList.size(); i++) {
			prodImg = prodImgList.get(i);	
			System.out.println(prodImg);
		}
		
		service.insertNeeds(prodImgList, classNeedsListVO);
//			
		}	
		
}