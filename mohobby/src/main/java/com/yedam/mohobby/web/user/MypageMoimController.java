package com.yedam.mohobby.web.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.mohobby.service.moim.MoimMemberVO;
import com.yedam.mohobby.service.moim.MoimVO;
import com.yedam.mohobby.service.user.MypageMoimService;
import com.yedam.mohobby.service.user.MypageMoimVO;

/**
 * @create 22/10/05
 * @author gabin
 * @title 회원 마이페이지 모임
 */
@RestController
@CrossOrigin(origins = "*")
public class MypageMoimController {

@Autowired
MypageMoimService service;

/**
 * 
 * @param memberId
 * @return List<MoimVO>
 * @title 유저가 공개중인 소모임 목록
 */
@GetMapping("/mypageprofilemoim/{memberId}")
public List<MoimVO> getProfileMoim(@PathVariable String memberId) {
	return service.getProfileMoim(memberId);
}

/**
 * @param moimMemberVO
 * @title 유저가 공개중인 소모임 목록 수정
 */
@PutMapping("/mypageprofilemoim")
public void updateProfileMoim(@RequestBody MoimMemberVO moimMemberVO) {
	service.updateProfileMoim(moimMemberVO);
}

/**
 * 
 * @param memberId
 * @param moimCatg
 * @return List<MypageMoimVO>
 * @title 유저가 참여, 운영중 소모임 목록(카테고리별 조회) / 참여중 memberRole 0/ 운영중 memberRole 1
 */
@PostMapping("/mypagemoim")
public List<MypageMoimVO> getMoim(@RequestBody MypageMoimVO mypageMoimVO) {
	return service.getMoim(mypageMoimVO);
}

}
