
package com.yedam.mohobby.service.moim;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.mohobby.service.communal.CommentsVO;
import com.yedam.mohobby.service.user.MemberVO;

@Service
public interface MoimService {
	// 소모임 단건조회
	public MoimVO getMoimOneInfo(int moimId);
	
	//소모임 등록
	public void moimInsert(MoimVO moimVO);
	
	//소모임 멤버 모집 조회(6개씩)
	public List<MoimVO> moimrecruitMember();
	
	//소모임 인기목록 조회
	public List<MoimVO> moimPopularSelect();
	
	//소모임명 조회
	public List<MoimVO> moimNameSelect();
	
	//소모임 카테고리 조회
	public List<MoimVO> moimCatgSelect();
	
	//소모임 종합 검색
	public List<MoimVO> moimAllSearch(String moimName, String moimCatg);
	
	//소모임 게시판 전체 리스트 조회
	public List<MoimBoardVO> moimAllBoard(int moimId, int boardType);
	
	//소모임 모임이름 중복체크
	public int memberIdCheck(String moimName);
	
	//소모임 공지사항 리스트 조회
	public List<MoimBoardVO> moimNoticeBoard(int moimId, int boardType);
	
	//소모임 게시글내 댓글 조회
	public List<MoimCommentVO> moimCommentAllList(int moimId, int boardType, int boardId);

	//소모임 게시글 단건조회
	public List<MoimBoardVO> moimOneBoard(int moimId, int boardType, int boardId);
	
	//소모임 게시글 댓글 등록
	public void moimCommentInsert(CommentsVO commVO);
	
	//소모임 댓글 수정
	public int moimCommentUpdate(CommentsVO commVO);

	//내가 참여한 소모임 목록
	public List<MoimVO> joinMoim(String memberId);
	
	//내가 운영중인 소모임 목록
	public List<MoimVO> operateMoim(String memberId);
	
	//소모임 게시글 댓글 삭제
	public int moimCommentDelete(int commId);
	
	//소모임 권한별 메인화면
	public int moimMainTop(String memberId);
	
	//소모임 등록 권한 업데이트
	public int moimUserUpdate(MemberVO vo);

	// 소모임 가입 회원 수 조회하는 로직
	public int moimMemberCount(int moimId);
	
	//소모임 단건조회
	public MoimVO getMoimInfo(int moimId);

	//소모임 N빵 전체 리스트 조회
	public List<MoimDutchVO> getAllDuchList(int moimId);
	
	//소모임 전체 멤버 리스트 조회
	public List<MoimMemberVO> getAllMemberList(int moimId);

	//소모임 멤버 검색 조회
	public List<MoimMemberVO> getSearchMember(String memberId, int moimId);
	
	//소모임 투표 디테일 리스트
	public List<MoimVoteListVO> moimVoteAllList(int moimId);

	//소모임 투표 아이템 리스트
	public List<MoimVoteListVO> moimVoteItemlist(int moimId);
	   
	//소모임 투표 아이템 선택 리스트
	public List<MoimVoteListVO> voteItemSelect(int moimId, String memberId);
	   
	//소모임 투표 결과
	public List<MoimVoteItemVO> votereulst(@Param("voteId")int voteId);
	   
	//소모임 투표
	public List<MoimVoteListVO> vote(@Param("voteId")int voteId, @Param("memberId")String memberId, @Param("itemSelect")int itemSelect, @Param("moimId")int moimId);
	   
	//소모임 투표 선택 
	public int UserSelectvote(MoimVoteListVO vo);

	//소모임 투표 중복검사
	public int voteCheck(MoimVoteListVO vo);
	
	//소모임 투표 선택 검사
	public List<MoimVoteListVO> selectCheck(@Param("moimId")int moimId, @Param("memberId")String memberId);		

	//소모임 n빵 등록
	public String dutchInsert(MoimDutchVO dutchVO);

	//소모임 n빵 참여자 등록
	public String dutchMemberInsert(MoimDutchPtpVO dutptpVO);

	//소모임 n빵 디테일 조회
	public List<MoimDutchVO> nbbangSelect(MoimDutchVO moimDutchVO);

	//소모임 일정 등록
	public String scheduleInsert(MoimScheduleVO scheduleVO);

	//소모임 일정 전체 조회
	public List<MoimScheduleVO> scheduleSelect(int moimId);
	
	//소모임 투표 항목
	public void insertVoteList(MoimVoteListVO vo);
  
	//소모임 일정 삭제
	public int deleteSchedule(int skedId);
	
	//소모임 가입 멤버 등록
	public String insertMember(MoimMemberVO momemVO);

	//소모임 가입조건 수정
	public String updateSetting(MoimVO moimVO);
  
    //html저장
    public void saveClassInfo(MoimInfoRequestVO req);
    
    //에디터 이미지 저장
    public void uploadMoimImage(MoimImageVO vo);
    
    //게시글 저장
    public int insertBoard(MoimBoardVO vo);

	public String readMoimInfo(int boardId);

	//소모임 삭제하기
	public String removeMoim(int moimId);

	//게시판 검색
	public List<MoimBoardVO> boardSearch(@Param("moimId")int moimId, @Param("boardType")int boardType, @Param("title")String title);
	
	//게시물 삭제
	public String deleteBoard(@Param("boardId")int boardId, @Param("boardType")int boardType);

	//소모임 프로필 수정
	public void updateProfile(MoimVO moimVO, List<MultipartFile> files);

	//n빵 삭제
	public void delNbbang(int dutchId);

	//n빵 체크 수정
	public void updateCalc(MoimDutchPtpSoloVO vo);
	
	//소모임 게시글 수정
	public void updateBoard(MoimBoardVO vo);
	
	//소모임 권한 확인
	public List<MoimMemberVO> moimRight(@Param("memberId")String memberId);
	
	//소모임 권한 확인
	public List<MoimMemberVO> moimnewRight(@Param("memberId")String memberId);
	
	//소모임 가입 확인
	public List<MoimMemberVO> joincheck(@Param("memberId")String memberId, @Param("moimId")int moimId);
	
	//소모임 가입자수 증가
	public int updatecnt(@RequestBody MoimVO vo);

	//소모임 프로필이미지 조회
	public String getImg(String memberId);
	
	//소모임 가입자수 감소
	public int deletecnt(@RequestBody MoimVO vo);
	
	//소모임 탈퇴
	public void delmoim(@Param("memberId")String memberId, @Param("moimId")int moimId);
	
	//소모임 관리자 검색
	public List<MoimVO> moimadmin(@Param("moimId")int moimId);
}

