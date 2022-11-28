package com.yedam.mohobby.web.sns;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
@RestController
@RequestMapping("/sns")
@CrossOrigin(origins = "*")
public class SnsController {
    
    @Autowired
    SnsService service;
    
    //게시물 등록
    @PostMapping(value = "/myfeed", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public String insertFeed(SnsPostVO snspostVO, SnsMediaVO snsmediaVO, List<MultipartFile> fileList) {
       
       service.regFeed(snspostVO, snsmediaVO, fileList);
        return "success";
    }
    
   //게시물 수정
    @PutMapping("/myfeed/{postId}")
    public String updateFeed(@PathVariable int postId, @RequestBody SnsPostVO snsPostVO) {
        try {
            snsPostVO.setPostId(postId);
            service.updateFeed(snsPostVO);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
    
   //비밀게시글로 전환
    @PutMapping("/myfeed/secret")
    public int changeSecPost(@RequestBody SnsPostVO snsPostVO) {
        System.out.println(snsPostVO.getPostId());
        return service.changeSecPost(snsPostVO);
    }  
    
   //피드 삭제
    @DeleteMapping("/myfeed/{postId}")
    public int deleteFeed(@PathVariable("postId") int postId){
          return service.deleteFeed(postId);
    }
    
   //인기강사피드조회
    //more기능 rownum가져오기(넘길 파라미너 : 마지막 번호)
    @GetMapping("/main/top20LecturerFeeds")
    public List<SnsPostVO> hotLecturerList() {
        return service.hotLecturerList();
    }
    
   //전체피드조회
    @GetMapping("/main/allFeeds")
    public List<SnsPostVO> allList(@RequestParam(value = "memberId", required = false) String memberId, @RequestParam(value = "moreBtn", required = false) String moreBtn, @RequestParam(value = "orderCond", required = false) String orderCond) {
        return service.allList(memberId, moreBtn, orderCond);
    }
    
   //최신피드조회
    @GetMapping("/main/newFeeds")
    public List<SnsPostVO> newList() {
        return service.newList();
    }
    
   //인기피드조회 - 테스트완료(약간 느림)
    @GetMapping("/main/hotFeeds")
    public List<SnsPostVO> hotList() {
        return service.hotList();
    }
    
   //해시태그 top6
    @GetMapping("/main/hashtag")
    public List<HashtagVO> selectHashtagForMain(){
        return service.selectHashtagForMain();
    }
    
   //팔로잉피드조회
    @GetMapping("/main/followingFeeds/{memberId}")
    public List<SnsPostVO> getFollowingFeeds(@PathVariable String memberId, @RequestParam(value = "moreBtn", required = false) String moreBtn) {
        return service.getFollowingFeeds(memberId, moreBtn);
    }
    
   //팔로잉제외 피드조회
    @GetMapping("/main/nofollowingFeeds/{memberId}")
    public List<SnsPostVO> getNoFollowingFeeds(@PathVariable String memberId, @RequestParam(value = "moreBtn", required = false) String moreBtn, @RequestParam(value = "orderCond", required = false) String orderCond) {
        return service.getNoFollowingFeeds(memberId, moreBtn, orderCond);
    }
    
   //내비밀글 모아보기
    @GetMapping("/user/archive/{memberId}")
    public List<SnsFeedVO> getMyArchive(@PathVariable String memberId) {
        return service.getMyArchive(memberId);
    }
    
   //프로필조회
    @GetMapping("/user/profile/{memberId}")
    public SnsProfileVO getProfile(@PathVariable("memberId") String memberId) {
           return service.getProfile(memberId);//컬럼명과 컬럼값이 키와 값으로 매핑이 된다.
    }
    
   //유저피드조회
    @GetMapping("/user/user_feeds/{memberId}")
    public List<SnsPostVO> getUserFeed(@PathVariable("memberId") String memberId, @RequestParam(value = "secPost", required = false) String secPost) {
        System.out.println("secPost: "+secPost);
    	return service.getUserFeed(memberId, secPost);
    }
    
    //유저취미키워드조회
    @GetMapping("/user/hobbies/{memberId}")
    public List<MemPickKeywordVO> getUserHobbies(@PathVariable String memberId){
        return service.getUserHobbies(memberId);
    }
    
   //피드상세조회
    @GetMapping("/user/feed_detail/{postId}")
    public SnsFeedVO getFeedDetail(@PathVariable("postId") int postId, @RequestParam(value = "memberId", required=false) String memberId) {
            return service.getFeedDetail(postId, memberId);
    }
    
   //피드상세이미지로드(컴포넌트)
    @GetMapping("/user/feed_detail_img/{postId}")
    public List<SnsMediaVO> getFeedImg(@PathVariable("postId") int postId) {
           return service.getFeedImg(postId);
    }
    
    //게시물전체 조회 페이징
   @GetMapping("/main/allFeed")
   public List<SnsPostVO> allListpageing(@RequestParam int firstIdx){
    	return service.allListPaging(firstIdx);
    } 
    
    /*
     * 해시태그
     */
   //해시태그 업데이트 or 삽입
    @PutMapping("/updateHashtag/{postId}")
    public String searchHashtag(@PathVariable int postId) {
        try {
            service.updateHashtag(postId);
            System.out.println("해시태그 처리 완료");
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("해시태그 처리 실패");
            return "fail";
        }
    }
    
    
    /*
     * 팔로우
     */
   //팔로우
    @PostMapping("/follow")
    public String follow(@RequestBody SnsFollowVO followVO) {
        try {
            service.follow(followVO);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";        
        }
    }
    
   //언팔로우
    @DeleteMapping("/follow/{followerId}/{followingId}")
    public String unfollow(@PathVariable String followerId, @PathVariable String followingId) {
        try {
            service.unfollow(followerId,followingId);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";        
        }
    }
    
   //팔로잉 목록 조회
    @GetMapping("/follow/search/following/{userId}")
    public List<SnsFollowVO> getFollowingList(@PathVariable String userId){
        return service.getFollowingList(userId);
    }
    
    
   //팔로워 목록 조회
    @GetMapping("/follow/search/follower/{userId}")
    public List<SnsFollowVO> getFollowerList(@PathVariable String userId){
        return service.getFollowerList(userId);
    }
    
   //팔로우 상태 체크
    @GetMapping("/follow/check")
    public int followCheck(@RequestParam String myId, @RequestParam String targetId) {
        return service.followCheck(myId, targetId);
    }

    
   //유저전체
    @GetMapping("/search/allUsers")
    public List<MemberVO> gerUsers() {
        return service.getUsers();
    }
    
   //유저아이디검색
    @GetMapping("/search/user")
    public List<MemberVO> searchUser(@RequestParam String memberId){
        return service.searchUser(memberId);
    }
    
   //유저닉네임검색
    @GetMapping("/search/nick/{nickname}")
    public List<MemberVO> getUsersByNick(@PathVariable String nickname){
        return service.getUsersByNick(nickname);
    }
    
   //해시태그검색
    @GetMapping("/search/hashtag")
    public List<SnsPostVO> searchHashtag(@RequestParam String hashtag){
        return service.searchHashtag(hashtag);
    }
    
    
    /*
     * 좋아요
     */
   //좋아요누적
    @PostMapping(path="/like")
    public int sumLikes(@RequestBody JjimVO jjimVO) {
        return service.sumLikes(jjimVO);
    }
    
    
    /*
     * 댓글
     */
   //댓글입력
    @PostMapping("/cmt") 
    public int insertCmt(@RequestBody CommentsVO commentsVO) {
    	return service.inserCmt(commentsVO);
    }
    
   //댓글수정
    @PutMapping("/cmt/{commId}")
    public int updateCmt(@PathVariable int commId, @RequestBody CommentsVO commentsVO) {
        commentsVO.setCommId(commId);
        return service.updateCmt(commentsVO);
    }
    
   //댓글삭제
    @DeleteMapping("/cmt/{commId}/{targetId}")
    public int deleteCmt(@PathVariable("commId") int commId, @PathVariable("targetId") int targetId){
    	return service.deleteCmt(commId, targetId);
    }
    
   //댓글조회
    @GetMapping("/cmt/{postId}")
    public List<CommentsVO> getCmtLists(@PathVariable int postId) {
        return service.getCmtLists(postId);
    }
    
    
    /*
     * 대댓글
     */
    //대댓입력
    @PostMapping("/recmt")
    public String insertReCmt(@RequestBody CommentsVO commentsVO) {
        try {
            service.insertReCmt(commentsVO);
            System.out.println(commentsVO);
            return "success"; 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("댓글입력 실패");
            return "fail";
        }
    }
    
    
    /*
     * 북마크
     */
    //컬렉션등록
    @PostMapping("/collection") 
    public int createBookmarkCtg(@RequestBody SnsBookmarkVO bmCtgVO) {
        return service.createBookmarkCtg(bmCtgVO);
    }
    
    //컬렉션이름수정
    @PutMapping("/collection")
    public int updateReCmt(@RequestBody SnsBookmarkVO bmCtgVO) {
        return service.updateBookmarkCtgName(bmCtgVO);
    }
    
    //컬렉션삭제
    @DeleteMapping("/collection/{catgId}")
    public int deleteBookmarkCtg(@PathVariable int catgId) {
        return service.deleteBookmarkCtg(catgId);
    }
    
    //컬렉션리스트 호출
    @GetMapping("/collection/{memberId}")
    public List<SnsBookmarkVO> getCollectionList(@PathVariable String memberId){
        return service.getBookmarkCtgs(memberId);
    }
    
    //북마크 등록
    @PostMapping("/collection/bookmark")
    public String addBookmark(@RequestBody SnsBookmarkVO bmVO) {
       try {
         service.addBookmark(bmVO);
         System.out.println("북마크완료");
         return "success";
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("북마크실패");
         return "fail";
      }
    }
    
    //북마크 삭제
    @DeleteMapping("/collection/bookmark/{postId}/{memberId}")
    public String deleteBookmark(@PathVariable int postId, @PathVariable String memberId) {
       try {
         service.deleteBookmark(postId, memberId);
         System.out.println("북마크삭제 완료");
         return "success";
      } catch (Exception e) {
          e.printStackTrace();
         System.out.println("북마크삭제 실패");
         return "fail";
      }
    }
    
    //북마크상태조회
    @GetMapping("/collection/bookmark/isBookmark/{postId}")
    public int isBookmark(@PathVariable int postId, @RequestParam String memberId) {
           return service.isBookmark(postId, memberId);
    }
    
    
    //북마크 이동
    @PutMapping("/collection/bookmark")
    public String changeCatg(@RequestBody SnsBookmarkVO bmkVO) {
        try {
            service.changeCatg(bmkVO);
            return "success";
          } catch (Exception e) {
              e.printStackTrace();
              return "fail";
          }
    }
    
    //컬렉션별 북마크 조회
    @GetMapping("/collection/bookmark/{catgId}")
    public List<SnsBookmarkVO> getBookmarks(@PathVariable int catgId){
         return service.getBookmarks(catgId);
    }
    
    //전체 북마크 조회
    @GetMapping("/collection/allBookmarks")
    public List<SnsBookmarkVO> getAllBookmarks(@RequestParam String memberId){
        return service.getAllBookmarks(memberId);
    }
    
    
    /*
     * 검색기록(펑예)
     */
    //검색기록 저장
    @PostMapping("/search/history")
    public String addHistory(@RequestBody SnsSearchHistoryVO historyVO) {
       try {
         service.addHistory(historyVO);
         System.out.println("검색기록 저장완료");
         return "success";
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("검색기록 저장실패");
         return "fail";
      }
    }
    //검색기록 리스트
    @GetMapping("/search/history/{memberId}")
    public List<SnsSearchHistoryVO> getHistoryList(@PathVariable String memberId){
        System.out.println("검색기록조회 완료");
        return service.getHistoryList(memberId);
    }
    //검색기록 삭제
    @DeleteMapping("/search/history/{searchId}")
    public String deleteHistory(@PathVariable int searchId) {
       try {
         service.deleteHistory(searchId);
         System.out.println("검색기록삭제 완료");
         return "success";
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("검색기록삭제 실패");
         return "fail";
      }
    }
    
}