package com.kitri.cafe.common.service;

public interface CommonService {

	// 전체 글 번호 증가
	int getNextSeq();
	// 조회수 증가
	void updateHit(int seq);
	
}
