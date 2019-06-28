package com.kitri.cafe.board.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kitri.cafe.board.dao.ReboardDao;
import com.kitri.cafe.board.model.ReboardDto;
import com.kitri.cafe.common.dao.CommonDao;
import com.kitri.cafe.util.CafeConstance;
import com.kitri.cafe.util.NumberCheck;

@Service
public class ReboardServiceImpl implements ReboardService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int writeArticle(ReboardDto reboardDto) {
		int cnt = sqlSession.getMapper(ReboardDao.class).writeArticle(reboardDto);
		return cnt != 0? reboardDto.getSeq() : 0;
	}

	@Override
	public List<ReboardDto> listArticle(Map<String, String> parameter) {
		
		// 페이지 체크 검사
		int pg = NumberCheck.NotNumberToOne(parameter.get("pg"));
		// end page구하기
		int end = pg * CafeConstance.ARTICLE_SIZE;
		int start = end - CafeConstance.ARTICLE_SIZE;
		
		parameter.put("start",start + "");
		parameter.put("end",end + "");
		
		
		return sqlSession.getMapper(ReboardDao.class).listArticle(parameter);
	}

	@Override
	@Transactional //알아서 트랜잭션해줌 root에서 관리.
	public ReboardDto viewArticle(int seq) {
		sqlSession.getMapper(CommonDao.class).updateHit(seq); // mapper_common 마이바티스 설정해주기
		ReboardDto reboardDto = sqlSession.getMapper(ReboardDao.class).viewArticle(seq);
		// 엔터키 적용해서 보내기.
		reboardDto.setContent(reboardDto.getContent().replace("\n", "<br>"));
		return reboardDto;
	}

	@Override
	@Transactional //댓글 페이지 이동 조회수 증가 등 x //알아서 롤백되는것인가?
	public ReboardDto getArticle(int seq) {
		return sqlSession.getMapper(ReboardDao.class).viewArticle(seq);
	}

	@Override
	public int modifyArticle(ReboardDto reboardDto) {
		return 0;
	}

	@Override
	public void deleteArticle(int seq) {

	}

	@Override
	@Transactional 
	public int replyArticle(ReboardDto reboardDto) {
		
		//update, insert, update순
		ReboardDao reboardDao = sqlSession.getMapper(ReboardDao.class);
		reboardDao.updateStep(reboardDto);
		reboardDao.replyArticle(reboardDto);
		reboardDao.updateReply(reboardDto.getPseq());
		
		return reboardDto.getSeq();
	}

}
