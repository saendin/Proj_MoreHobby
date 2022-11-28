package com.yedam.mohobby.service.communal;

import lombok.Data;

@Data
//서브키워드
public class SubKeyword {
    private int subKeywordId; //서브키워드 아이디
    private String keywordId; //키워드 아이디
    private String subKeywordName; //서브키워드 이름
}
