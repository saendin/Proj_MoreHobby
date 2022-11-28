package com.yedam.mohobby.serviceImpl.moim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.mohobby.mapper.moim.MoimMapper;
import com.yedam.mohobby.service.communal.CommentsVO;
import com.yedam.mohobby.service.moim.MoimBoardVO;
import com.yedam.mohobby.service.moim.MoimCommentVO;
import com.yedam.mohobby.service.moim.MoimDutchPtpSoloVO;
import com.yedam.mohobby.service.moim.MoimDutchPtpVO;
import com.yedam.mohobby.service.moim.MoimDutchVO;
import com.yedam.mohobby.service.moim.MoimImageVO;
import com.yedam.mohobby.service.moim.MoimInfoRequestVO;
import com.yedam.mohobby.service.moim.MoimMemberVO;
import com.yedam.mohobby.service.moim.MoimScheduleVO;
import com.yedam.mohobby.service.moim.MoimService;
import com.yedam.mohobby.service.moim.MoimVO;
import com.yedam.mohobby.service.moim.MoimVoteItemVO;
import com.yedam.mohobby.service.moim.MoimVoteListVO;
import com.yedam.mohobby.service.user.MemberVO;
import com.yedam.mohobby.web.classes.ClassController;
import com.yedam.mohobby.web.moim.MoimController;

@Service
public class MoimServiceImpl implements MoimService {

	@Autowired
	MoimMapper mapper;

	// 소모임 단건조회
	@Override
	public MoimVO getMoimOneInfo(int moimId) {
		return mapper.getMoimOneInfo(moimId);
	}
	
	// 소모임 등록
	@Override
	public void moimInsert(MoimVO moimVO) {
		System.out.println(moimVO);
		try {
			moimVO.setMoimImg(moimVO.getMoimName() + ".jpg");
		} catch (NullPointerException e) {
			moimVO.setMoimImg("comfuck.jpg");
		}
		 mapper.moimInsert(moimVO);
	}

	// 소모임 멤버 모집 조회(6개씩)
	@Override
	public List<MoimVO> moimrecruitMember() {
		return mapper.moimrecruitMember();
	}

	// 인기소모임 조회
	@Override
	public List<MoimVO> moimPopularSelect() {
		return mapper.moimPopularSelect();
	}

	// 소모임명 조회
	@Override
	public List<MoimVO> moimNameSelect() {
		return mapper.moimNameSelect();
	}

	// 소모임 카테고리 조회
	@Override
	public List<MoimVO> moimCatgSelect() {
		return mapper.moimCatgSelect();
	}

	// 소모임 종합 검색
	@Override
	public List<MoimVO> moimAllSearch(String moimName, String moimCatg) {
		HashMap<String, String> map = new HashMap<>();
		map.put("moimName", moimName);
		map.put("moimCatg", moimCatg);
		return mapper.moimAllSearch(map);
	}

	// 소모임 게시판 전체 리스트 조회
	@Override
	public List<MoimBoardVO> moimAllBoard(int moimId, int boardType) {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("moimId", moimId);
		map.put("boardType", boardType);
		return mapper.moimAllBoard(map);
	}

	// 소모임명 중복 체크
	@Override
	public int memberIdCheck(String moimName) {
		return mapper.moimIdCheck(moimName);
	}

	// 소모임 공지사항 리스트 출력
	@Override
	public List<MoimBoardVO> moimNoticeBoard(int moimId, int boardType) {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("moimId", moimId);
		map.put("boardType", boardType);
		return mapper.moimNoticeBaord(map);
	}

	// 소모임 게시글내 댓글 단건조회
	@Override
	public List<MoimCommentVO> moimCommentAllList(int moimId, int boardType, int boardId) {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("moimId", moimId);
		map.put("boardType", boardType);
		map.put("boardId", boardId);
		System.out.println(moimId);
		System.out.println(boardType);
		System.out.println(boardId);
		return mapper.moimCommentAllList(map);
	}

	// 소모임 게시글 단건 조회
	@Override
	public List<MoimBoardVO> moimOneBoard(int moimId, int boardType, int boardId) {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("moimId", moimId);
		map.put("boardType", boardType);
		map.put("boardId", boardId);
		return mapper.moimOneBoard(map);
	}

	// 소모임 게시글 댓글 등록
	@Override
	public void moimCommentInsert(CommentsVO commVO) {
		mapper.moimCommentInsert(commVO);
	}

	// 내가 참여중인 소모임 리스트
	@Override
	public List<MoimVO> joinMoim(String memberId) {
		return mapper.joinMoim(memberId);
	}

	// 소모임 댓글 수정
	@Override
	public int moimCommentUpdate(CommentsVO commVO) {
		return mapper.moimCommentUpdate(commVO);
	}

	// 내가 운영중인 소모임 리스트
	@Override
	public List<MoimVO> operateMoim(String memberId) {
		return mapper.operateMoim(memberId);
	}

	// 소모임 권한별 메인화면
	@Override
	public int moimMainTop(String memberId) {
		return mapper.moimMainTop(memberId);
	}

	// 소모임 등록 권한 업데이트
	@Override
	public int moimUserUpdate(MemberVO vo) {
		return mapper.moimUserUpdate(vo);
	}

	// 소모임 단건조회
	@Override
	public MoimVO getMoimInfo(int moimId) {
		return mapper.getMoimOneInfo(moimId);
	}

	// 소모임 게시글 댓글 삭제
	@Override
	public int moimCommentDelete(int commId) {
		System.out.println("serviceimpl" + commId);
		return mapper.moimCommentDelete(commId);
	}

	// 소모임 가입 회원 수 조회하는 로직
	@Override
	public int moimMemberCount(int moimId) {
		return mapper.moimMemberCount(moimId);
	}

	// N빵 전체 리스트 조회
	@Override
	public List<MoimDutchVO> getAllDuchList(int moimId) {
		return mapper.getAllDuchList(moimId);
	}

	// 소모임 전체 멤버 리스트 조회
	@Override
	public List<MoimMemberVO> getAllMemberList(int moimId) {
		return mapper.getAllMemberList(moimId);
	}

	// 소모임 멤버 검색 조회
	@Override
	public List<MoimMemberVO> getSearchMember(String memberId, int moimId) {
		return mapper.getSearchMember(memberId, moimId);
	}

	// 소모임 투표 디테일 리스트
	@Override
	public List<MoimVoteListVO> moimVoteAllList(int moimId) {
		return mapper.moimVoteAllList(moimId);
	}

	// 소모임 투표 아이템 리스트
	@Override
	public List<MoimVoteListVO> moimVoteItemlist(int moimId) {
		return mapper.moimVoteItemlist(moimId);
	}

	// 투표 아이템 선택
	@Override
	public List<MoimVoteListVO> voteItemSelect(int moimId, String memberId) {
		return mapper.voteItemSelect(moimId, memberId);
	}

	// 투표결과
	@Override
	public List<MoimVoteItemVO> votereulst(int voteId) {
		return mapper.votereulst(voteId);
	}

	// 투표
	@Override
	public List<MoimVoteListVO> vote(int voteId, String memberId, int itemSelect, int moimId) {
		return mapper.vote(voteId, memberId, itemSelect, moimId);
	}

	// 유저 선택 투표 조회
	@Override
	public int UserSelectvote(MoimVoteListVO vo) {
		return mapper.UserSelectvote(vo);
	}

	// 투표 참여 조회
	@Override
	public int voteCheck(MoimVoteListVO vo) {
		return mapper.voteCheck(vo);
	}

	// 투표 선택
	@Override
	public List<MoimVoteListVO> selectCheck(int moimId, String memberId) {
		return mapper.selectCheck(moimId, memberId);
	}

	// n빵 등록
	@Override
	public String dutchInsert(MoimDutchVO dutchVO) {
		return mapper.dutchInsert(dutchVO);
	}

	// n빵 참여자 등록
	@Override
	public String dutchMemberInsert(MoimDutchPtpVO dutptpVO) {

		List<String> memberIdList = dutptpVO.getMemberId();
		int sum = 0;

		for (String memberId : memberIdList) {
			MoimDutchPtpSoloVO vo = new MoimDutchPtpSoloVO();
			vo.setCalcCheck(dutptpVO.getCalcCheck());
			vo.setCalcPrice(dutptpVO.getCalcPrice());
			vo.setDutchId(dutptpVO.getDutchId());
			vo.setMemberId(memberId);
			vo.setMoimId(dutptpVO.getMoimId());

			sum += mapper.dutchMemberInsert(vo);
		}

		if (sum == memberIdList.size()) {
			return "success";
		}

		return "fail";
	}

	// 소모임 n빵 디테일 조회
	@Override
	public List<MoimDutchVO> nbbangSelect(MoimDutchVO moimDutchVO) {
		return mapper.nbbangSelect(moimDutchVO);
	}

	// 소모임 투표 항목
	@Override
	public void insertVoteList(MoimVoteListVO vo) {

		List<MoimVoteItemVO> itemList = vo.getVotelist();

		MoimVoteListVO body = new MoimVoteListVO();
		body.setTopic(vo.getTopic());
		body.setEndDate(vo.getEndDate());
		body.setMoimId(vo.getMoimId());
		body.setMemberId(vo.getMemberId());
		mapper.insertVoteList(body);

		int voteId = body.getVoteId();

		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i).getContent() != null) {
				body.setVoteId(voteId);
				body.setContent(itemList.get(i).getContent());
			}
			mapper.insertVoteListFor(body);
		}

	}

	// 소모임 일정 등록
	@Override
	public String scheduleInsert(MoimScheduleVO scheduleVO) {
		return mapper.scheduleInsert(scheduleVO);
	}

	// 소모임 일정 전체조회
	@Override
	public List<MoimScheduleVO> scheduleSelect(int moimId) {
		return mapper.scheduleSelect(moimId);
	}

	// 소모임 일정 삭제
	@Override
	public int deleteSchedule(int skedId) {
		return mapper.deleteSchedule(skedId);
	}

	// 소모임 가입 멤버 등록
	@Override
	public String insertMember(MoimMemberVO momemVO) {
		return mapper.insertMember(momemVO);
	}

	// 소모임 가입조건 수정
	@Override
	public String updateSetting(MoimVO moimVO) {
		return mapper.updateSetting(moimVO);
	}

	// html 저장
	@Override
	public void saveClassInfo(MoimInfoRequestVO req) {
		String path = MoimController.class.getResource("/").getPath();
		path = path.substring(0, path.lastIndexOf("mohobby"));
		path = path.substring(0, path.lastIndexOf("mohobby") + "mohobby".length());

		path += "/mohobby/mohobby/src/assets/html/moimInfo/";

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}
		path += req.getFilename();
		path += ".html";
		System.out.println(path);

		File file = new File(path);

		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(req.getContent().getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 에디터 이미지 저장
	@Override
	public void uploadMoimImage(MoimImageVO vo) {
		System.out.println(vo);
		File file = null;

		String path = ClassController.class.getResource("/").getPath();
		path = path.substring(0, path.lastIndexOf("mohobby"));
		path = path.substring(0, path.lastIndexOf("mohobby") + "mohobby".length());

		path += "/mohobby/mohobby/src/assets/image/moim/board/";
		// path += String.valueOf(vo.getFoldername());
		// path += "/";

		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdir();
		}

		byte[] targetBytes = null;
		FileOutputStream fos = null;

		if (vo.getSrc().contains("base64")) {
			System.out.println(vo.getSrc().split(",")[1]);
			String data = vo.getSrc().split(",")[1];
			targetBytes = data.getBytes();
			byte[] bytes = Base64.getDecoder().decode(targetBytes);

			try {
				file = new File(path + "/" + vo.getFilename() + ".jpg");
				fos = new FileOutputStream(file);
				fos.write(bytes);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("기존파일");
		}

	}

	// 게시판 작성
	@Override
	public int insertBoard(MoimBoardVO vo) {
		return mapper.insertBoard(vo);
	}

	// 게시글 불러오기
	@Override
	public String readMoimInfo(int boardId) {
		String path = ClassController.class.getResource("/").getPath();
		path = path.substring(0, path.lastIndexOf("mohobby"));
		path = path.substring(0, path.lastIndexOf("mohobby") + "mohobby".length());

		path += "/mohobby/mohobby/src/assets/html/moimInfo/";
		path += String.valueOf(boardId);
		path += ".html";

		File file = new File(path);
		String res = "";

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));

			String str;

			while ((str = br.readLine()) != null) {
				res += str;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;
	}

	// 소모임 삭제
	@Override
	public String removeMoim(int moimId) {
		return mapper.removeMoim(moimId);
	}

	// 게시글 검색

	@Override
	public List<MoimBoardVO> boardSearch(int moimId, int boardType, String title) {
		return mapper.searchBoard(moimId, boardType, title);
	}

	// 소모임 프로필 수정
	@Override
	public void updateProfile(MoimVO moimVO, List<MultipartFile> files) {
		try {
			// 저장할 경로
			String path = this.getClass().getResource("/").getPath();
			System.out.println(path);
			path = path.substring(0, path.lastIndexOf("mohobby"));
			path = path.substring(0, path.lastIndexOf("mohobby") + "mohobby".length());
			path += "/mohobby/mohobby/src/assets/image/moim/";

			// 진짜 진짜 파일 이름
			path += moimVO.getMoimName();
			path += ".jpg";

			// 새로 path만든 값은 save에 저장
			for (int i = 0; i < files.size(); i++) {

				MultipartFile file = files.get(i);
				File save = new File(path);

				file.transferTo(save);
			}

			// 소모임 정보 수정
			mapper.updateProfile(moimVO);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("업로드 실패: " + e.getMessage());
		}
	}

	// 게시글 삭제
	@Override
	public String deleteBoard(int boardId, int boardType) {
		return mapper.deleteBoard(boardId, boardType);
	}
	
	//n빵 삭제
	@Override
	public void delNbbang(int dutchId) {
		mapper.delNbbang(dutchId);
	}
	
	//n빵 체크 수정
	@Override
	public void updateCalc(MoimDutchPtpSoloVO vo) {
		mapper.updateCalc(vo);
	}
	
	//게시글 수정
	@Override
	public void updateBoard(MoimBoardVO vo) {
	   mapper.updateBoard(vo);   
	}


	//소모임 권한 확인
	@Override
	public List<MoimMemberVO> moimRight(String memberId) {
	   return mapper.moimRight(memberId);
	}
	
	//소모임 권한 확인
	@Override
	public List<MoimMemberVO> moimnewRight(String memberId) {
	   return mapper.moimnewRight(memberId);
	}

	//소모임 가입 체크
	@Override
	public List<MoimMemberVO> joincheck(String memberId, int moimId) {
	    return mapper.joincheck(memberId, moimId);
	}

	//가입자수 증가
	@Override
	public int updatecnt(MoimVO vo) {
		return mapper.updatecnt(vo);
	}

	//소모임 프로필 이미지 조회
	@Override
	public String getImg(String memberId) {
		return mapper.getImg(memberId);
	}
	
	//가입자수 증가
	@Override
	public int deletecnt(MoimVO vo) {
	   return mapper.deletecnt(vo);
	}
	
	//소모임 탈퇴
	@Override
	public void delmoim(String memberId, int moimId) {
		mapper.delmember(memberId, moimId);
	}

	//소모임 관리자 검색
	@Override
	public List<MoimVO> moimadmin(int moimId) {
		return mapper.moimadmin(moimId);
	}
	
	
	
}