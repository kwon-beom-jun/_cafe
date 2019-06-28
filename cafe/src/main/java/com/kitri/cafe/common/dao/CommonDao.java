package com.kitri.cafe.common.dao;

import java.util.Map;

public interface CommonDao {

	// 전체 글 번호 증가
	public int getNextSeq();
	// 조회수 증가
	public void updateHit(int seq);
	public int getNewArticleCount(int bcode);
	public int getTotalArticleCount(Map<String, String> parameter); // bcode, key , word 글검색한것까지 생각한것임
	
}
