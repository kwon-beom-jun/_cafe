package com.kitri.cafe.common.dao;

public interface CommonDao {

	// 전체 글 번호 증가
	public int getNextSeq();
		// 조회수 증가
	public void updateHit(int seq);
	
}
