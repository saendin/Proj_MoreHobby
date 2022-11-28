package com.yedam.mohobby.web.user;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.mohobby.service.admin.MemberVO;
import com.yedam.mohobby.service.communal.KeywordVO;
import com.yedam.mohobby.service.user.MemberService;

/**
 * @create 2022/10/04
 * @author gabin
 * @title 회원 관리
 */
@RestController
@CrossOrigin(origins = "*")
public class MemberController {
	
	String newFileName;
	String extension;
	
	@Autowired
	MemberService service;
	
	/**
	 * 
	 * @param memberId
	 * @return MemberVO
	 * @title 회원 한명 정보 가져오기(회원 마이페에지, 회원 아이디 있는지 조회)
	 */
	@GetMapping("/member/{memberId}")
	public MemberVO getMember(@PathVariable String memberId) {
		return service.getMember(memberId);
	}
	
	/**
	 * 
	 * @param memberVO
	 * @return MemberVO
	 * @title 로그인 아이디, 비밀번호가 일치하는지 체크
	 */
	@PostMapping("/membercheck")
	public MemberVO checkIdPassword(@RequestBody MemberVO memberVO) {
		return service.checkIdPassword(memberVO);
	}
	
	/**
	 * 
	 * @param email
	 * @return MemberVO
	 * @title 이메일 통해서 회원인지 조회
	 */
	@GetMapping("/memberEmail/{email}")
		public MemberVO getMemberByEmail(@PathVariable String email) {
			return service.getMemberByEmail(email);
		}
	
	
	/**
	 * 
	 * @return List<MemberVO>
	 * @title 회원정보 전체 조회
	 */
	@GetMapping("/memberAll")
		public List<MemberVO> getAllMember() {
			return service.getAllMember();
	}
	
	/**
	 * 
	 * @return List<KeywordVO>
	 * @title 지역전체 조회 
	 */
	@GetMapping("/regionAll")
		public List<KeywordVO> getRegion() {
			return service.getRegion();
		}
	
	/**
	 * 
	 * @param memberVO
	 * @title 회원가입 insert
	 */
	@PostMapping("/member")
	public void insertMember(@RequestBody MemberVO memberVO) {
		System.out.println(memberVO.getNickName());
		service.insertMember(memberVO);
	}
	
	/**
	 * 
	 * @param memberId
	 * @title 회원탈퇴 날짜 update 
	 */
	@PutMapping("/memberdelete")
	public void deleteMember(@RequestBody MemberVO memberVO) {
		service.deleteMember(memberVO.getMemberId());
	}
	
	/**
	 * 
	 * @param memberVO
	 * @title 회원정보 수정 update
	 */
	@PutMapping("/memberupdate")
	public void updateMemxber(@RequestBody MemberVO memberVO) {
		System.out.println(memberVO.getBirth());
		System.out.println(memberVO.getBirth());
		System.out.println(memberVO.getBirth());
		System.out.println(memberVO.getBirth());
		System.out.println(memberVO.getBirth());
		service.updateMember(memberVO);
	}
	
	/**
	 * @title 프로필 이미지 update
	 */
	  //게시물 등록 - 파일등록 처리중..
    @PostMapping(value = "/memberProfileUpdate", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String insertProfile(@RequestPart(value="file") List<MultipartFile> files) {
        try {
           MultipartFile file = files.get(0);
           
          String path = this.getClass().getResource("/").getPath();
          System.out.println( path);
          path = path.substring(0, path.lastIndexOf("mohobby"));
          path = path.substring(0, path.lastIndexOf("mohobby")+"mohobby".length());
          path += "/mohobby/mohobby/src/assets/image/user/";
          
          	
         //진짜 파일 이름
         String fileRealName = file.getOriginalFilename();
         String[] fileRealNameList = fileRealName.split("[.]");
         newFileName = "";
         for(String name : fileRealNameList) {
        	 System.out.println(name);
         }
         
         for(int i = 0; i < fileRealNameList.length-1; i++) {
        	 newFileName += fileRealNameList[i];
         }
         
         System.out.println("가공한 fileRealName" + fileRealName);
         //확장자를 추출
         extension = fileRealName.substring(fileRealName.indexOf("."), fileRealName.length()).toLowerCase();
         
         System.out.println("저장할 폴더 경로: " + path);
         System.out.println("실제 파일명: " + newFileName + extension);
         System.out.println("확장자: " + extension);
         
       
         //업로드한 파일을 서버 컴퓨터의 지정한 경로에 저장
         File saveFile = new File(path + newFileName + extension);
         file.transferTo(saveFile);
         
         return newFileName + extension;
         
         
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("업로드 실패: " + e.getMessage());
         return "fail";
      }
    }
    
    @PutMapping("/memberupdateprofilename")
    public void updateProfile(@RequestBody MemberVO memberVO) {
    	
    	System.out.println(newFileName+extension);
    	memberVO.setProfileImg(newFileName+extension);
    	service.updateProfile(memberVO);
    	
    }
	
	
	/**
	 * 
	 * @param memberVO
	 * @title 회원 비밀번호 수정
	 */
	@PutMapping("/memberupdatepassword")
	public void updatePassword(@RequestBody MemberVO memberVO) {
		service.updatePassword(memberVO);
	}
	
	/**
	 * 
	 * @param memberVO
	 * @title 챌린지 등급 update(plsql만들기)
	 */
	@PutMapping("/memberupdatechallgrade")
	public void updateChallGrade(@RequestBody MemberVO memberVO) {
		service.updateChallGrade(memberVO);
	}
	
	/**
	 * 
	 * @param memberVO
	 * @title role변환(멋진활동가, 만능 재주꾼, 일반회원)
	 */
	@PutMapping("/memberupdaterole")
	public void updateRole(@RequestBody MemberVO memberVO) {
		service.updateRole(memberVO);
	}
}
