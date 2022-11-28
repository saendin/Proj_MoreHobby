package com.yedam.mohobby.serviceImpl.sns;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.mohobby.mapper.sns.SnsMapper;
import com.yedam.mohobby.service.communal.CommentsVO;
import com.yedam.mohobby.service.communal.HashtagVO;
import com.yedam.mohobby.service.communal.JjimVO;
import com.yedam.mohobby.service.sns.SnsBookmarkVO;
import com.yedam.mohobby.service.sns.SnsFeedVO;
import com.yedam.mohobby.service.sns.SnsFollowVO;
import com.yedam.mohobby.service.sns.SnsMediaVO;
import com.yedam.mohobby.service.sns.SnsPostVO;
import com.yedam.mohobby.service.sns.SnsProfileVO;
import com.yedam.mohobby.service.sns.SnsSearchHistoryVO;
import com.yedam.mohobby.service.sns.SnsService;
import com.yedam.mohobby.service.user.MemPickKeywordVO;
import com.yedam.mohobby.service.user.MemberVO;
/**
 * @create 22/10/08
 * @author sunjin
 * @title sns controller
 */

@ControllerAdvice
@Service
public class SnsServiceImpl implements SnsService{

    @Autowired
    SnsMapper mapper;
   /*
     * 게시물
   */
    //게시물 아이디 생성
   @Override
   public int getPostId() {
      return mapper.getPostId();
   }

   
    //미디어 등록
    @Transactional
    @Override
    public boolean regFeed(SnsPostVO snspostVO, SnsMediaVO snsmediaVO, List<MultipartFile> fileList) {
       
        snspostVO.setPostId(getPostId());
        
       try {
          MultipartFile getFirstFile = fileList.get(0);
          String getFirstFileName = getFirstFile.getOriginalFilename();
         
         //확장자를 추출
         String type = getFirstFileName.substring(getFirstFileName.indexOf("."), getFirstFileName.length());
          
         //고유번호로 변환된 파일이름
         getFirstFileName = "0" + type;
          
         //snsPost info 등록
         SnsPostVO snsVo = new SnsPostVO();
         snsVo.setPostId(snspostVO.getPostId());
         snsVo.setMemberId(snspostVO.getMemberId());
         snsVo.setContent(snspostVO.getContent());
         snsVo.setHashtag(snspostVO.getHashtag());
         snsVo.setThumbnail(getFirstFileName);
         
         mapper.insertFeed(snsVo);
          
         //hashtag 등록 및 업데이트
         HashtagVO hashtagVO = new HashtagVO();
         mapper.updateHashtag(snspostVO.getPostId());
         
         
          //경로찾기
          String path = this.getClass().getResource("/").getPath();
          System.out.println(path);
          path = path.substring(0, path.lastIndexOf("mohobby_prj"));
          System.out.println(path);
//          path = path.substring(0, path.lastIndexOf("mohobby")+"mohobby".length());
          System.out.println(path);
          
          //폴더 이름 = post_id
          int dirName = snspostVO.getPostId();
          
          //저장할 폴더 경로
          path += "mohobby_prj/mohobby/mohobby/src/assets/image/sns/" + dirName;
          
          File folder = new File(path);
          if(!folder.exists()) {
             folder.mkdir(); //폴더가 존재하지 않을시 생성
          }
          
          for(int i = 0; i < fileList.size(); i++) {
             
             MultipartFile file = fileList.get(i);
             
             //진짜 파일 이름
             String fileRealName = file.getOriginalFilename();
             
             //확장자를 추출
             String extension = fileRealName.substring(fileRealName.indexOf("."), fileRealName.length());
             
             //파일이름 = 인덱스번호 + 확장자
             String fileName = i + extension;
             
             //업로드한 파일을 서버 컴퓨터의 지정한 경로에 저장
             File saveFile = new File(path + "/" + fileName);
             
             file.transferTo(saveFile);
             
             SnsMediaVO mediaVo = new SnsMediaVO();
             mediaVo.setPostId(snspostVO.getPostId());
             mediaVo.setFileName(fileName);
             mediaVo.setFileRealName(fileRealName);
             mediaVo.setExtension(extension);
             mediaVo.setPath(path);
             mediaVo.setDirName(dirName);
             
             mapper.insertMedia(mediaVo);
          }
       } catch (IllegalStateException e) {
          e.printStackTrace();
       } catch (IOException e) {
          e.printStackTrace();
       }
       return false;
       
    }
    
   //게시물 수정
   @Override
   public int updateFeed(SnsPostVO snsPostVO) {
      return mapper.updateFeed(snsPostVO);
   }
   
   //비밀게시글로 전환
   @Override
   public int changeSecPost(SnsPostVO snsPostVO) {
       return mapper.changeSecPost(snsPostVO);
   }
   
   //피드 삭제
   @Override
   public int deleteFeed(int postId) {
      return mapper.deleteFeed(postId);
   }
   
   //미디어 삭제
//   @Override
//   public int deleteMedia(int postId) {
//       
//       //경로찾기
//       String path = this.getClass().getResource("/").getPath();
//       path = path.substring(0, path.lastIndexOf("mohobby"));
//       path = path.substring(0, path.lastIndexOf("mohobby")+"mohobby".length());
//       
//       
//       path += "/mohobby/mohobby/src/assets/image/sns/" + postId;
//
//       
//       File deleteFile = new File(path);
//       
//       // 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
//       if(deleteFile.exists()) {
//           
//           // 파일을 삭제합니다.
//           deleteFile.delete(); 
//           
//           System.out.println("파일을 삭제하였습니다.");
//           
//       } else {
//           System.out.println("파일이 존재하지 않습니다.");
//       }
//
//       return 0;
//   }
   
   //인기강사피드조회
   @Override
   public List<SnsPostVO> hotLecturerList() {
      return mapper.hotLecturerList();
   }
   
   //전체피드조회
   @Override
   public List<SnsPostVO> allList(String memberId, String moreBtn, String orderCond) {
      return mapper.allList(memberId, moreBtn, orderCond);
   }
   
   //최신피드조회
   @Override
   public List<SnsPostVO> newList() {
      return mapper.newList();
   }
   
   //인기피드조회
   @Override
   public List<SnsPostVO> hotList() {
      return mapper.hotList();
   }
   
    //팔로잉피드조회
    public List<SnsPostVO> getFollowingFeeds(String memberId, String moreBtn){
        return mapper.getFollowingFeeds(memberId, moreBtn);
    }

    //프로필조회
    @Override
    public SnsProfileVO getProfile(String memberId) {
        return mapper.getProfile(memberId);
    }
    
    //팔로잉 제외 조회
    @Override
    public List<SnsPostVO> getNoFollowingFeeds(String memberId, String moreBtn, String orderCond) {
        return mapper.getNoFollowingFeeds(memberId, moreBtn, orderCond);
    }
    
    //내 피드 모아보기
    @Override
    public List<SnsFeedVO> getMyArchive(String memberId) {
        return mapper.getMyArchive(memberId);
    }
        
    //유저피드조회
    @Override
    public List<SnsPostVO> getUserFeed(String memberId, String secPost) {
        return mapper.getUserFeed(memberId, secPost);
    }
    
    //유저키워드조회
    @Override
    public List<MemPickKeywordVO> getUserHobbies(String memberId) {
        return mapper.getUserHobbies(memberId);
    }
    
   //피드상세조회
   @Override
   public SnsFeedVO getFeedDetail(int postId, String memberId){
        return mapper.getFeedDetail(postId, memberId);
   }
   
   //이미지 조회
   @Override
   public List<SnsMediaVO> getFeedImg(int postId) {
      return mapper.getFeedImg(postId);
   }
   
   
    /*
     * 해시태그
     */
   //해시태그 업데이트 or 삽입
   @Override
   public int updateHashtag(int postId) {
      return mapper.updateHashtag(postId);
   }
   
   
    /*
     * 팔로우
     */
   
   //팔로우
   @Override
   public int follow(SnsFollowVO followVO) {
      return mapper.follow(followVO);
   }
   
   //언팔로우
   @Override
   public int unfollow(String followerId, String followingId) {
      return mapper.unfollow(followerId, followingId);
   }
   
   //팔로잉 목록 조회
   @Override
   public List<SnsFollowVO> getFollowingList(String userId) {
      return mapper.getFollowingList(userId);
   }
  
   
   //팔로워 목록 조회
   @Override
   public List<SnsFollowVO> getFollowerList(String userId) {
      return mapper.getFollowerList(userId);
   }
   
   //필로우 상태 조회
   @Override
   public int followCheck(String myId, String targetId) {
       return mapper.followCheck(myId, targetId);
   }
   
   
   
   //유저전체
   @Override
   public List<MemberVO> getUsers() {
      return mapper.getUsers();
   }
   
   //유저검색
   @Override
   public List<MemberVO> searchUser(String memberId) {
      return mapper.searchUser(memberId);
   }
   
   //유저닉네임검색
    @Override
    public List<MemberVO> getUsersByNick(String nickname) {
        return mapper.getUsersByNick(nickname);
    }
    
   //해시태그검색
   @Override
   public List<SnsPostVO> searchHashtag(String hashtag) {
      return mapper.searchHashtag(hashtag);
   }
   
   //인기해시태그
    @Override
    public List<HashtagVO> selectHashtagForMain() {
        return mapper.selectHashtagForMain();
    }
   
    
    /*
     * 좋아요
     */

   //좋아요누적
   @Override
   public int sumLikes(JjimVO jjimVO) {
      return mapper.sumLikes(jjimVO);
   }
   
   
    /*
     * 댓글
     */
   //댓글입력
   @Override
   public int inserCmt(CommentsVO commentsVO) {
      return mapper.inserCmt(commentsVO);
   }
   
   //댓글수정
   @Override
   public int updateCmt(CommentsVO commentsVO) {
      return mapper.updateCmt(commentsVO);
   }
   
   //댓글삭제
   @Override
   public int deleteCmt(int commId, int targetId) {
       System.out.println(commId);
      return mapper.deleteCmt(commId, targetId);
   }
   
   //댓글조회
   @Override
   public List<CommentsVO> getCmtLists(int postId) {
      return mapper.getCmtLists(postId);
   }
    
   
    /*
     * 대댓글
     */
   
   //대댓입력
   @Override
   public int insertReCmt(CommentsVO commentsVO) {
      return mapper.insertReCmt(commentsVO);
   }
   
   //대댓수정
   @Override
   public int updateReCmt(CommentsVO commentsVO) {
      return mapper.updateReCmt(commentsVO);
   }

   
    /*
     * 북마크
     */
   
    //컬렉션 등록
    @Override
    public int createBookmarkCtg(SnsBookmarkVO bmkCtgVO) {
        return mapper.createBookmarkCtg(bmkCtgVO);
    }

    //컬렉션 이름수정
    @Override
    public int updateBookmarkCtgName(SnsBookmarkVO bmkCtgVO) {
        return mapper.updateBookmarkCtgName(bmkCtgVO);
    }
    
    //컬렉션 삭제(안의 게시물도 전부 삭제되도록)
    @Override
    public int deleteBookmarkCtg(int catgId) {
        return mapper.deleteBookmarkCtg(catgId);
    }
    
    //컬렉션 목록
    @Override
    public List<SnsBookmarkVO> getBookmarkCtgs(String memberId) {
        return mapper.getBookmarkCtgs(memberId);
    }
    
    //북마크 등록
    @Override
    public int addBookmark(SnsBookmarkVO bmkVO) {
        return mapper.addBookmark(bmkVO);
    }
    
    
    //북마크 이동
    @Override
    public int changeCatg(SnsBookmarkVO bmkVO) {
        return mapper.changeCatg(bmkVO);
    }
    
    //북마크 삭제
    @Override
    public int deleteBookmark(int postId, String memberId) {
        return mapper.deleteBookmark(postId, memberId);
    }
    
    //북마크 여부조회
    @Override
    public int isBookmark(int postId, String memberId) {
        return mapper.isBookmark(postId, memberId);
    }
    
    //북마크 조회
    @Override
    public List<SnsBookmarkVO> getBookmarks(int catgId) {
        return mapper.getBookmarks(catgId);
    }
    
    //전체 북마크
    public List<SnsBookmarkVO> getAllBookmarks(String memberId){
       return mapper.getAllBookmarks(memberId);
    }
    
    
    /*
     * 검색기록
     */
    
    //검색기록 등록
    @Override
    public int addHistory(SnsSearchHistoryVO historyVO) {
        return mapper.addHistory(historyVO);
    }
    
    //검색기록 리스트
    @Override
    public List<SnsSearchHistoryVO> getHistoryList(String memberId) {
        return mapper.getHistoryList();
    }
    
    //검색기록 삭제
    @Override
    public int deleteHistory(int searchId) {
        return mapper.deleteHistory(searchId);
    }
    
    //전체 피드 조회 페이징
	@Override
	public List<SnsPostVO> allListPaging(int firstIdx) {
		System.out.println("firstIdx : " + firstIdx);
		return mapper.allListPaging(firstIdx);
	}






}