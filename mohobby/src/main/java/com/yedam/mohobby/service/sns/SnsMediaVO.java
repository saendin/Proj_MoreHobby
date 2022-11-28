package com.yedam.mohobby.service.sns;

import lombok.Data;

@Data
public class SnsMediaVO {
	private int mediaId; //미디어고유번호
	private int postId; //포스트아이디
	private int dirName; //폴더이름
	private String extension; //확장자
	private String path; //url
	private String fileName; //file이름+uuid이름
	private String fileRealName; //파일진짜이름
}
