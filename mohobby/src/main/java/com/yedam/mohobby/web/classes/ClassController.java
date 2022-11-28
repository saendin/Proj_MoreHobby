
package com.yedam.mohobby.web.classes;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yedam.mohobby.service.classes.ClassAttendanceVO;
import com.yedam.mohobby.service.classes.ClassBoardVO;
import com.yedam.mohobby.service.classes.ClassChapterVO;
import com.yedam.mohobby.service.classes.ClassCurriculumVO;
import com.yedam.mohobby.service.classes.ClassImageVO;
import com.yedam.mohobby.service.classes.ClassInfoRequestVO;
import com.yedam.mohobby.service.classes.ClassListRequestVO;
import com.yedam.mohobby.service.classes.ClassNeedsVO;
import com.yedam.mohobby.service.classes.ClassPayVO;
import com.yedam.mohobby.service.classes.ClassReviewVO;
import com.yedam.mohobby.service.classes.ClassService;
import com.yedam.mohobby.service.classes.ClassesVO;
import com.yedam.mohobby.service.communal.JjimVO;

/**
 * 
 * @author 최은경
 * 강의 서비스 관련 컨트롤러
 *
 */
@RestController
@CrossOrigin(origins = "*")
public class ClassController {
	@Autowired
	ClassService classService;
	
	//에디터 이미지 저장
	@PostMapping("/uploadClassImage")
	public void uploadClassImage(@RequestBody ClassImageVO req) {
	    classService.uploadClassImage(req);
	}
	
	//html 파일 생성
	@PostMapping("/saveClassInfo")
	public void saveClassInfo(@RequestBody ClassInfoRequestVO req) {
		classService.saveClassInfo(req);
	}
	
	//html 파일 불러오기
	@GetMapping("/readClassInfo")
	public String readClassInfo(@RequestParam int classId) {
	    return classService.readClassInfo(classId);
	}
	
	
	
	//QR코드 생성
	@GetMapping(
	        value = "/AttdQR",
	        produces = MediaType.IMAGE_PNG_VALUE
	        )
	public @ResponseBody String createCodeImg(
	        @RequestParam String link
	){
		
	    return classService.createCodeImg(link);
	}
	
	
	//강의 전체 조회
	/**
	 * 
	 * @param catg
	 * @param memberId
	 * @return
	 */
	@GetMapping("/class/{catg}")
	public @ResponseBody List<ClassesVO> listAll(
	        @PathVariable String catg,
	        @RequestParam String memberId
	) {
	    ClassListRequestVO req = new ClassListRequestVO();
	    req.setCatg(catg);
	    req.setMemberId(memberId);
	    return classService.listAll(req);
	}
	
	//모집중인 강의 조회
	@GetMapping("/class/on")
	public @ResponseBody List<ClassesVO> listOnClass() {
		return classService.listOnClass();
	}
	
	//강의 단건 조회
	/**
	 * 
	 * @param classId
	 * @return
	 */
	@GetMapping("/class/detail/{classId}")
	public @ResponseBody ClassesVO listOne(@PathVariable int classId,  @RequestParam String memberId) {
	    ClassListRequestVO req = new ClassListRequestVO();
	    req.setClassId(classId);
	    req.setMemberId(memberId);
	    return classService.listOne(req);
	}
	
	
	//후기평가조회
	@GetMapping("/class/review")
	public @ResponseBody ClassReviewVO getClassReview(@RequestParam int classId) {
		return classService.getClassReview(classId);
	}
	
	//강의게시글리스트
	@GetMapping("/class/board")
	public @ResponseBody List<ClassBoardVO> getClassReviewList(
			@RequestParam(required=true) int classId, 
			@RequestParam(required=true) int boardType,
			@RequestParam(required=false) String memberId) {
		return classService.getClassBoardList(classId, boardType, memberId);
	}
	
	//강의게시글등록
	@PostMapping("/class/board")
	public ClassBoardVO addClassBoard(@RequestBody ClassBoardVO board) {
	    return classService.addClassBoard(board);
	}
	
	//강의게시글삭제
	@DeleteMapping("/class/board")
	public void deleteClassBoard(@RequestParam int boardId) {
	    classService.deleteClassBoard(boardId);
	}
	
	//강의게시글수정
	@PutMapping("/class/board")
	public void updateClassBoard(@RequestBody ClassBoardVO board) {
	    classService.updateClassBoard(board);
	}
	
	//강의 커리큘럼 단건 조회
	@GetMapping("/class/learn/{currId}")
	public @ResponseBody ClassCurriculumVO getCurrInfo(
			@PathVariable int currId, 
			@RequestParam(required=false) String memberId) {
		return classService.getCurrInfo(currId, memberId);
	}
	
	//강의 커리큘럼 진행율 조회
	@GetMapping("/class/learn/progress/{currId}")
	public @ResponseBody ClassAttendanceVO getCurrProgress(@PathVariable int currId, @RequestParam String memberId) {
		ClassAttendanceVO vo = new ClassAttendanceVO();
		vo.setCurrId(currId);
		vo.setMemberId(memberId);
		classService.getCurrProgress(vo);
		return vo;
	}
	
	//강의 커리큘럼 단건 진행율 업데이트
	@PutMapping("/class/learn/update")
	public void updateAttdInfo(@RequestBody ClassAttendanceVO vo) {
		classService.updateAttdInfo(vo);
	}
	
	//강의 결제내역 추가
	@PostMapping("/class/pay")
    public int insertClassPayOne(@RequestBody ClassPayVO payResult) {
		System.out.println(payResult);
    	return classService.insertClassPayOne(payResult);
    }
	
	//강의 결제내역 단건 조회
	@GetMapping("/class/pay/{classId}")
	public @ResponseBody ClassPayVO getClassPayOne(@PathVariable int classId, @RequestParam String memberId) {
		ClassPayVO vo = new ClassPayVO();
		vo.setClassId(classId);
		vo.setMemberId(memberId);
		return classService.getClassPayOne(vo);
	}
	
	//강의 준비물 조회
	@GetMapping("/class/pay/needs/{classId}")
	public @ResponseBody List<ClassNeedsVO> getClassNeedsInfo(@PathVariable int classId) {
		return classService.getClassNeedsInfo(classId);
	}
	
	//찜 등록
	@PostMapping("/class/jjim")
	public void addJjim(@RequestBody JjimVO jjim) {
	    classService.addJjim(jjim);
	}
	
	//찜 등록 취소
	@DeleteMapping("/class/jjim")
	public void delJjim(@RequestBody JjimVO jjim) {
	    classService.deleteJjim(jjim);
	}
	
	// 강의챕터조회
	@GetMapping("/class/chapterList")
	 public List<ClassChapterVO> getChapterList(
			 @RequestParam(required=true) int classId, 
			 @RequestParam(required=false) String memberId) {
        return classService.getChapterList(classId, memberId);
    }
	
	//내 강의 목록 조회
	@GetMapping("/class/my")
    public List<ClassesVO> getMyCourse(
    		@RequestParam(required = true) String memberId, 
    		@RequestParam(required = false) int classStatus
    		) {
    	return classService.getMyCourse(memberId, classStatus);
    }
	
	//내 수료증 발급 가능한 강의 목록 조회
	@GetMapping("/class/my/cert")
    public List<ClassesVO> getMyCourseCertificate(@RequestParam String memberId) {
		return classService.getMyCourseCertificate(memberId);
	}
	
	//강의 개설 신청
	@PostMapping(value="/class/open", consumes= {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	public void openClassForm(
			ClassesVO classVO
			,MultipartFile mainImage
			,List<MultipartFile> subImageList
			,ClassInfoRequestVO contentVO
			,String chapListJson
			,String currListJson
			,List<MultipartFile> videoList
			) {
		
		
		Gson gson = new Gson();
		List<ClassChapterVO> chapList = gson.fromJson(chapListJson, new TypeToken<List<ClassChapterVO>>(){}.getType());
		List<ClassCurriculumVO> currList = gson.fromJson(currListJson, new TypeToken<List<ClassCurriculumVO>>(){}.getType());
		
		classService.openClassForm(classVO, mainImage, subImageList, contentVO, chapList, currList, videoList);
	}
	
	
	//출결 정보 조회
	@GetMapping("/class/attd")
	public @ResponseBody ClassAttendanceVO getAttendanceInfo(@RequestParam String memberId) {
		return classService.getAttendanceInfo(memberId);
	}
	
	//출결 정보 입력
	@PostMapping("/class/attd")
	public void insertAttendanceInfo(@RequestBody HashMap<String, String> req) {
		System.out.println("컨트롤러");
		System.out.println(req);
		classService.insertAttendanceInfo(req);
	}

}

