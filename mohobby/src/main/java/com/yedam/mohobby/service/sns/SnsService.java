package com.yedam.mohobby.service.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.mohobby.service.communal.CommentsVO;
import com.yedam.mohobby.service.communal.HashtagVO;
import com.yedam.mohobby.service.communal.JjimVO;
import com.yedam.mohobby.service.user.MemPickKeywordVO;
import com.yedam.mohobby.service.user.MemberVO;
/**
 * @create 22/10/08
 * @author sunjin
 * @title sns service
 */
@Service
public interface SnsService {
   /*
     * 게시물
     */
    
    //게시물 등록 전 포스트아이디 가져오기
    public int getPostId();
   
    //게시물 등록
    public boolean regFeed(SnsPostVO snspostVO, SnsMediaVO snsmediaVO, List<MultipartFile> fileList);
     
    //게시물 수정
    public int updateFeed(SnsPostVO snsPostVO);
    
    //비밀 게시글로 전환
    public int changeSecPost(SnsPostVO snsPostVO);
    
    //피드 삭제
    public int deleteFeed(int postId);
    
    //인기강사피드
    public List<SnsPostVO> hotLecturerList();
    
    //전체피드
    public List<SnsPostVO> allList(String memberId, String moreBtn, String orderCond);
    
    //최신피드
    public List<SnsPostVO> newList();
    
    //인기피드
    public List<SnsPostVO> hotList();
    
    //팔로잉피드조회
    public List<SnsPostVO> getFollowingFeeds(String memberId, String moreBtn);
    
    //팔로잉 제외
    public List<SnsPostVO> getNoFollowingFeeds(String memberId, String moreBtn, String orderCond);
    
    //내 비밀글 모아보기
    public List<SnsFeedVO> getMyArchive(String memberId);
    
    //피드프로필
    public SnsProfileVO getProfile(String memberId);
    
    //유저피드조회
    public List<SnsPostVO> getUserFeed(String memberId, String secPost);
    
    //유저취미조회
    public List<MemPickKeywordVO> getUserHobbies(String memberId);
    
    //피드상세조회
    public SnsFeedVO getFeedDetail(int postId, String memberId);
    
    //피드이미지조회
    public List<SnsMediaVO> getFeedImg(int postId);
    
    //피드전체조회 페이징
    public List<SnsPostVO> allListPaging(int firstIdx);
    
    /*
     * 해시태그
     */
    //해시태그 업데이트 or 삽입
    public int updateHashtag(int postId);
    
    
    /*
     * 팔로우
     */
    //팔로우
    public int follow(SnsFollowVO followVO);
    
    //언팔로우
    public int unfollow(String followerId, String followingId);
    
    //팔로잉 목록 조회
    public List<SnsFollowVO> getFollowingList(String userId);
    
    //팔로워 목록 조회
    public List<SnsFollowVO> getFollowerList(String userId);
    
    //필로우 상태 조회
    public int followCheck(String myId, String targetId);
    
    //유저전체
    public List<MemberVO> getUsers();
    
    //유저검색
    public List<MemberVO> searchUser(String memberId);
    
    //유저닉네임검색
    public List<MemberVO> getUsersByNick(String nickname);
    
    //해시태그검색
    public List<SnsPostVO> searchHashtag(String hashtag);
    
    //인기해시태그
    public List<HashtagVO> selectHashtagForMain();

    
    /*
     * 좋아요
     */

    //좋아요누적
    public int sumLikes(JjimVO jjimVO);
    
    /*
     * 댓글
     */
    //댓글입력
    public int inserCmt(CommentsVO commentsVO);
    
    //댓글수정
    public int updateCmt(CommentsVO commentsVO);
    
    //댓글삭제
    public int deleteCmt(int commId, int targetId);
    
    //댓글조회
    public List<CommentsVO> getCmtLists(int postId);
    
    
    /*
     * 대댓글
     */
    //대댓입력
    public int insertReCmt(CommentsVO commentsVO);
    
    //대댓수정
    public int updateReCmt(CommentsVO commentsVO);
    
    
    /*
     * 북마크컬렉션
     */
    //컬렉션 등록
    public int createBookmarkCtg(SnsBookmarkVO bmkCtgVO);
    
    //컬렉션 이름수정
    public int updateBookmarkCtgName(SnsBookmarkVO bmkCtgVO);
    
    //컬렉션 삭제(안의 게시물도 전부 삭제되도록)
    public int deleteBookmarkCtg(int catgId);
    
    //컬렉션 목록
    public List<SnsBookmarkVO> getBookmarkCtgs(String memberId);
    
    
    /*
     * 북마크
     */
    //북마크 등록
    public int addBookmark(SnsBookmarkVO bmkVO);
    
    //북마크 삭제
    public int deleteBookmark(int postId, String memberId);
    
    //북마크 이동
    public int changeCatg(SnsBookmarkVO bmkVO);
    
    //북마크 여부조회
    public int isBookmark(int postId, String memberId);
    
    //북마크 조회
    public List<SnsBookmarkVO> getBookmarks(int catgId);
    
    //북마크 전체조회
    public List<SnsBookmarkVO> getAllBookmarks(String memberId);
   
    
   /*
    * 검색기록
    */
   //검색기록 등록
   public int addHistory(SnsSearchHistoryVO historyVO);
   
   //검색기록 리스트
   public List<SnsSearchHistoryVO> getHistoryList(String memberId);
   
   //검색기록 삭제
   public int deleteHistory(int searchId);
   
//   test

//    // [DBMapper.xml 쿼리에서 선언한 변수 개수 및 타입에 맞게 파라미터 선언]
//   public int saveImage(Insert_DB_Image_Model userImage); // 리턴값으로 int 사용 [상태값 확인 성공, 실패 여부]
//
//
//    // [DBMapper.xml 쿼리에서 선언한 변수 개수 및 타입에 맞게 파라미터 선언]
//   public Map<String, Object> selectImage(int idx); // 리턴값으로 map 사용 [출력]

}