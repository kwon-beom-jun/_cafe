package com.kitri.cafe.board.service;

import java.util.List;
import java.util.Map;

import com.kitri.cafe.board.model.MemoDto;

// 댓글형태
public interface MemoService {

	void writeMemo(MemoDto memoDto); // 댓글은 쓰자마자 목록에 나오므로 반환값 없음
	String listMemo(int seq); // 댓글이 포함되는 글번호 최신순 이런거 할거면 맵으로 냅둠
//	MemoDto viewMemo(int seq); 댓글보기 없음
	void modifyMemo(MemoDto memoDto);
	String deleteMemo(int seq, int mseq); // 메모글에 글번호로 지워라
	
}
