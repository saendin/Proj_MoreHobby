package com.yedam.mohobby.mapper.admin;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yedam.mohobby.service.admin.AdminClassVO;
import com.yedam.mohobby.service.classes.ClassBoardVO;
import com.yedam.mohobby.service.classes.ClassNeedsVO;
import com.yedam.mohobby.service.classes.ClassesVO;

@Mapper
public interface AdminClassMapper {

		// 강의 목록
		public List<AdminClassVO> getAuditClassList();
		
		// 승인 신청한 강의 정보(vue에서 상세 설명 경로 가지고오기)
		
		// 신청 결과 업로드
		public void updateAuditStatus(ClassesVO classesVO);

		// 강사, 강의, 정산 정보, 지급 여부 등등
		
		// qna리스트 전체 가지고 오기
		public List<ClassBoardVO> getAllQnAList();
		
		// 선택한 클래스의 qna리스트 가지고 오기
		public List<ClassBoardVO> getQnAList(int classId);
		
		// qna답변 여부 업데이트
		public void updateQnAAnswer(ClassBoardVO classBoardVO);
		
		// 강의 개설 신청 시 등록한 준비물 목록 조회
		public ClassesVO getApplyNeeds(int classId);
		
		// 강의 수료증 발급 여부, 발급 기준 업데이트
		public void updateCert(ClassesVO classesVO);
		
		// 강의 준비물 insert
		public void insertNeeds(ClassNeedsVO classNeedsVO);
}