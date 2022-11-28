package com.yedam.mohobby.mapper.sns;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
import com.yedam.mohobby.service.user.MemPickKeywordVO;
import com.yedam.mohobby.service.user.MemberVO;
/**
 * @create 22/10/08
 * @author sunjin
 * @title sns controller
 */
public interface SnsMapper {
    
   /*
     * 게시물
     */
    //postId의 selectKey
    public int getPostId();
    
    //게시물 등록
    public int insertFeed(SnsPostVO snsPostVO);
    
    //미디어 등록
    public int insertMedia(SnsMediaVO snsMediaVO);
    
    //게시물 수정
    public int updateFeed(SnsPostVO snsPostVO);
    
    //비밀 게시글로 전환
    public int changeSecPost(SnsPostVO snsPostVO);
    
    //피드 삭제
    public int deleteFeed(int postId);
    
    //인기강사피드조회
    public List<SnsPostVO> hotLecturerList();
    
    //전체피드조회
    public List<SnsPostVO> allList(@Param("memberId") String memberId, @Param("moreBtn") String moreBtn, @Param("orderCond") String orderCond);
 
    //전체피드조회페이징
    public List<SnsPostVO> allListPaging();
    
    //최신피드조회
    public List<SnsPostVO> newList();
    
    //인기피드조회
    public List<SnsPostVO> hotList();
    
    //팔로잉피드조회
    public List<SnsPostVO> getFollowingFeeds(@Param("memberId") String memberId, @Param("moreBtn") String moreBtn);
    
    //팔로잉 제외 피드조회
    public List<SnsPostVO> getNoFollowingFeeds(@Param("memberId") String memberId, @Param("moreBtn") String moreBtn, @Param("orderCond") String orderCond);
    
    //내 비밀글 모아보기
    public List<SnsFeedVO> getMyArchive(String memberId);
    
    //프로필조회
    public SnsProfileVO getProfile(String memberId);
    
    //유저피드조회
    public List<SnsPostVO> getUserFeed(@Param("memberId") String memberId, @Param("secPost") String secPost);
    
    //유저취미조회
    public List<MemPickKeywordVO> getUserHobbies(String memberId);
    
    //인기해시태그
    public List<HashtagVO> selectHashtagForMain();
    
    //피드상세조회
    public SnsFeedVO getFeedDetail(@Param("postId") int postId, @Param("memberId") String memberId);
    
    //피드이미지조회
    public List<SnsMediaVO> getFeedImg(int postId);
    
    //전체조회페이징
    public List<SnsPostVO> allListPaging(@Param("firstIdx") int firstIdx);
    
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
    public int unfollow(@Param("followerId") String followerId, @Param("followingId") String followingId);
    
    //팔로잉 목록 조회
    public List<SnsFollowVO> getFollowingList(String userId);
    
    //팔로워 목록 조회
    public List<SnsFollowVO> getFollowerList(String userId);
    
    //필로우 상태 조회
    public int followCheck(@Param("myId")String myId, @Param("targetId") String targetId);
    
    //유저전체
    public List<MemberVO> getUsers();
    
    //유저 아이디검색
    public List<MemberVO> searchUser(@Param("memberId")String memberId);
    
    //유저닉네임검색
    public List<MemberVO> getUsersByNick(String nickname);
    
    //해시태그검색
    public List<SnsPostVO> searchHashtag(String hashtag);

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
    public int deleteCmt(@Param("commId")int commId, @Param("targetId") int targetId);
   
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
     * 북마크
     */
    //컬렉션 등록
    public int createBookmarkCtg(SnsBookmarkVO bmkCtgVO);
    
    //컬렉션 이름수정
    public int updateBookmarkCtgName(SnsBookmarkVO bmkCtgVO);
    
    //컬렉션 삭제(안의 게시물도 전부 삭제되도록)
    public int deleteBookmarkCtg(int catgId);
   
    //컬렉션 목록
    public List<SnsBookmarkVO> getBookmarkCtgs(String memberId);
    
    //북마크 이동
    public int addBookmark(SnsBookmarkVO bmkVO);
    
    //북마크 카테고리 이동
    public int changeCatg(SnsBookmarkVO bmkVO);
    
    //북마크 삭제
    public int deleteBookmark(@Param("postId")int postId, @Param("memberId") String memberId);
    
    //북마크 여부조회
    public int isBookmark(@Param("postId")int postId, @Param("memberId") String memberId);
    
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
    public List<SnsSearchHistoryVO> getHistoryList();
    
    //검색기록 삭제
    public int deleteHistory(int searchId);

   
   


}   