package com.kitri.cafe.board.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kitri.cafe.board.model.MemoDto;

@Service
public class MemoServiceImpl implements MemoService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void writeMemo(MemoDto memoDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<MemoDto> listMemo(Map<String, String> parameter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void modifyMemo(MemoDto memoDto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMemo(int mseq) {
		// TODO Auto-generated method stub
		
	}

	

}
