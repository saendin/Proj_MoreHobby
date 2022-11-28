package com.yedam.mohobby.serviceImpl.admin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.mohobby.mapper.admin.AdminClassMapper;
import com.yedam.mohobby.service.admin.AdminClassService;
import com.yedam.mohobby.service.admin.AdminClassVO;
import com.yedam.mohobby.service.admin.ClassNeedsListVO;
import com.yedam.mohobby.service.classes.ClassBoardVO;
import com.yedam.mohobby.service.classes.ClassNeedsVO;
import com.yedam.mohobby.service.classes.ClassesVO;

@Service
public class AdminClassServiceImpl implements AdminClassService {

	@Autowired
	AdminClassMapper mapper;
	String newFileName = "";
	String extension;

	// 강의 목록
	@Override
	public List<AdminClassVO> getAuditClassList() {
		return mapper.getAuditClassList();
	}

	// 승인 신청한 강의 정보(vue에서 상세 설명 경로 가지고오기)
	
	// 신청 결과 업로드
	@Override
	public void updateAuditStatus(ClassesVO classesVO) {
		mapper.updateAuditStatus(classesVO);
	}
	
	// 강사, 강의, 정산 정보, 지급 여부 등등
	
	// qna리스트 전체 가지고 오기
	public List<ClassBoardVO> getAllQnAList() {
		return mapper.getAllQnAList();
	}
	
	// 선택한 클래스의 qna리스트 가지고 오기
	@Override
	public List<ClassBoardVO> getQnAList(int classId) {
		return mapper.getQnAList(classId);
	}
		
	// qna답변 여부 업데이트
	@Override
	public void updateQnAAnswer(ClassBoardVO classBoardVO) {
		mapper.updateQnAAnswer(classBoardVO);
	}
	
	// 강의 개설 신청 시 등록한 준비물 목록 조회
	@Override
	public ClassesVO getApplyNeeds(int classId) {
		return mapper.getApplyNeeds(classId);
	}

	// 강의 수료증 발급 여부, 발급 기준 업데이트
	@Override
	public void updateCert(ClassesVO classesVO) {
		mapper.updateCert(classesVO);
		
	}
	
	// 강의 준비물 insert
	public void insertNeeds(List<MultipartFile> prodImgList, ClassNeedsListVO classNeedsListVO) {
		
		MultipartFile prodImg = null;
		for(int i = 0; i < prodImgList.size(); i++) {
				 // 이미지 파일 저장하기 
				 // 이미지 하나의 정보를 가져오기 
		           prodImg = prodImgList.get(i);
		           
		        // 파일 경로 설정
		          String path = this.getClass().getResource("/").getPath();
		          System.out.println( path);
		          path = path.substring(0, path.lastIndexOf("mohobby"));
		          path = path.substring(0, path.lastIndexOf("mohobby")+"mohobby".length());
		          path += "/mohobby/mohobby/src/assets/image/class/needs";
		          
		          	
		         //진짜 파일 이름
		         String fileRealName = prodImg.getOriginalFilename();
		         
		         // 파일이름 가공하기 
		         String[] fileRealNameList = fileRealName.split("[.]");
		         newFileName = "";
		         for(String name : fileRealNameList) {
		        	 System.out.println(name);
		         }
		         for(int j = 0; j < fileRealNameList.length-1; j++) {
		        	 newFileName += fileRealNameList[j];
		         }

		         //확장자를 추출
		         extension = fileRealName.substring(fileRealName.indexOf("."), fileRealName.length()).toLowerCase();
		         
		         // 결과물 출
		         System.out.println("저장할 폴더 경로: " + path);
		         System.out.println("실제 파일명: " + newFileName + extension);
		         System.out.println("확장자: " + extension);
		         
		       
		         //업로드한 파일을 서버 컴퓨터의 지정한 경로에 저장
		         File saveFile = new File(path + newFileName + extension);
		         try {
					prodImg.transferTo(saveFile);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		         
		         // 준비물 내용 뽑아내기 
		         classNeedsListVO.getList().get(i);
		         
		         String prodName = classNeedsListVO.getList().get(i).getProdName();
		         int prodPrice = classNeedsListVO.getList().get(i).getProdPrice();
		         int classId = classNeedsListVO.getList().get(i).getClassId();
		         
		         // 셋팅 후 mapper에 넘기
		         ClassNeedsVO classNeedsVO = new ClassNeedsVO();
		         classNeedsVO.setProdImg(newFileName + extension);
		         classNeedsVO.setProdName(prodName);
		         classNeedsVO.setProdPrice(prodPrice);
		         classNeedsVO.setClassId(i);
		         mapper.insertNeeds(classNeedsVO);
		       
		}
	
	}
}
