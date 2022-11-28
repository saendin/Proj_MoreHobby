package com.yedam.mohobby.service.classes;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.mohobby.service.communal.JjimVO;

@Service
public interface ClassService {
	//강의개설
	public void insertClass(ClassesVO classes);
	
	//전체조회
	public List<ClassesVO> listAll(ClassListRequestVO req);
	
	//모집중인 강의 조회
	public List<ClassesVO> listOnClass();
	
	//단건조회
    public ClassesVO listOne(ClassListRequestVO req);
    
    //후기평가조회
    public ClassReviewVO getClassReview(int classId);
    
    //강의게시글조회
    public List<ClassBoardVO> getClassBoardList(int classId, int boardType, String memberId);
    
    //강의게시글등록
    public ClassBoardVO addClassBoard(ClassBoardVO board);
    
    //강의게시글수정
    public void updateClassBoard(ClassBoardVO board);
    
    //강의게시글삭제
    public void deleteClassBoard(int boardId);

	//찜등록
    public void addJjim(JjimVO jjim);

    //찜삭제
    public void deleteJjim(JjimVO jjim);
    
    //강의 챕터 조회
    public List<ClassChapterVO> getChapterList(int classId, String memberId);
    
    //강의 커리큘럼 진행율 조회
    public ClassAttendanceVO getCurrProgress(ClassAttendanceVO vo);
    
    //강의 커리큘럼 단건 조회
    public ClassCurriculumVO getCurrInfo(int currId, String memberId);
    
    //강이 커리큘럼 단건 업데이트
    public void updateAttdInfo(ClassAttendanceVO vo);
    
    //강의 결제내역 추가
    public int insertClassPayOne(ClassPayVO vo);
    
    //강의 결제내역 단건조회
    public ClassPayVO getClassPayOne(ClassPayVO vo);
    
    //강의 준비물 조회
    public List<ClassNeedsVO> getClassNeedsInfo(int classId);
    
    //html저장
    public void saveClassInfo(ClassInfoRequestVO req);
    
    //html경로 읽기
    public String readClassInfo(int classId);
    
    //qr코드생성
    public String createCodeImg(String link);
    
    //에디터 이미지 저장
    public void uploadClassImage(ClassImageVO vo);
    
    //내 강의 목록 조회
    public List<ClassesVO> getMyCourse(String memberId, int classStatus);
    
    //내 수료증 발급 가능한 강의 목록 조회
    public List<ClassesVO> getMyCourseCertificate(String memberId);
    
    //강의개설신청
    public void openClassForm(
			ClassesVO classVO, 
			MultipartFile mainImage, 
			List<MultipartFile> subImageList, 
			ClassInfoRequestVO contentVO,
			List<ClassChapterVO> chapList,
			List<ClassCurriculumVO> currList,
			List<MultipartFile> videoList
			);
    
    //강의 썸네일 저장
    public void saveThumbnail(int classId, MultipartFile mainImage, List<MultipartFile> subImageList);
    
    //강의 영상 저장
    public void saveVideos(List<MultipartFile> videoList, List<Integer> currIdList);
    
    //출결 관리 정보 가져오기
    public ClassAttendanceVO getAttendanceInfo(String memberId);
    
    //출결 정보 입력
    public void insertAttendanceInfo(HashMap<String, String> req);

}
