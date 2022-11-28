package com.yedam.mohobby.mapper.classes;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yedam.mohobby.service.classes.ClassAttendanceVO;
import com.yedam.mohobby.service.classes.ClassBoardVO;
import com.yedam.mohobby.service.classes.ClassChapterVO;
import com.yedam.mohobby.service.classes.ClassCurriculumVO;
import com.yedam.mohobby.service.classes.ClassListRequestVO;
import com.yedam.mohobby.service.classes.ClassNeedsVO;
import com.yedam.mohobby.service.classes.ClassPayVO;
import com.yedam.mohobby.service.classes.ClassReviewVO;
import com.yedam.mohobby.service.classes.ClassesVO;
import com.yedam.mohobby.service.communal.JjimVO;

@Mapper
public interface ClassMapper {
	//강의개설
	public void insertClass(ClassesVO classes);
	
	//챕터생성
	public void insertChaper(ClassChapterVO chap);
	
	//커리큘럼생성
	public void insertCurriculum(ClassCurriculumVO curr);
	
	//전체조회
	public List<ClassesVO> listAll(ClassListRequestVO req);
	
	//모집중인 강의 조회
	public List<ClassesVO> listOnClass();
	
	//단건조회
	public ClassesVO listOne(ClassListRequestVO req);
	
	//후기평가조회
	public ClassReviewVO getClassReview(int classId);
	
	//강의게시글조회
	public List<ClassBoardVO> getClassBoardList(
			@Param("classId") int classId, 
			@Param("boardType") int boardType,
			@Param("memberId") String memberId);
	
	//강의게시글등록
	public void addClassBoard(ClassBoardVO board);
	
	//강의게시글수정
	public void updateClassBoard(ClassBoardVO board);
	
	//강의게시글삭제
	public void deleteClassBoard(int boardId);

	//찜등록
    public void addJjim(JjimVO jjim);

    //찜등록
    public void deleteJjim(JjimVO jjim);

    //강의 챕터 조회
    public List<ClassChapterVO> getChapterList(ClassChapterVO vo);
    
    //강의 결제내역 추가
    public int insertClassPayOne(ClassPayVO vo);
    
    //강의 결제내역 단건조회
    public ClassPayVO getClassPayOne(ClassPayVO vo);
    
    //강의 준비물 조회
    public List<ClassNeedsVO> getClassNeedsInfo(int classId);
    
    //강의 커리큘럼 진행율 조회
    public ClassAttendanceVO getCurrProgress(ClassAttendanceVO vo);
    
    //커리큘럼 단건조회
    public ClassCurriculumVO getCurrInfo(
    		@Param("currId") int currId, 
    		@Param("memberId") String memberId);
    
    public void updateAttdInfo(ClassAttendanceVO vo);
    
    //내 강의 목록 조회
    public List<ClassesVO> getMyCourse(ClassPayVO reqBody);
    
    //내 강의 진행율 조회
    public HashMap<String, Integer> getMyCourseProgress(
    		@Param("classId") int classId, 
    		@Param("memberId") String memberId);
    
    //내 수료증 발급 가능한 강의 목록 조회
    public List<ClassesVO> getMyCourseCertificate(String memberId);
    
    
    
    /*
     * 출석관리
     */
    //오늘 기준 커리큘럼 출결 정보
    public ClassAttendanceVO getTodayAttendanceInfo(String memberId);
    //오늘 기준 출결 통계
    public ClassAttendanceVO getTodayAttendanceCalc(@Param("memberId")String memberId, @Param("classId")int classId);
    //오늘 기준 과정 진행율
    public ClassAttendanceVO getTodayClassProgress(int classId);
    //수업없는 날 강의 정보
    public ClassAttendanceVO getNoneClassInfo(String memberId);
    //출결 정보 입력
    public void insertAttendanceInfo(HashMap<String, String> req);
    //출결 정보 수정
    public void updateAttendanceInfo(HashMap<String, String> req);
    
    
    /*
     * 작성 게시글
     */
}
