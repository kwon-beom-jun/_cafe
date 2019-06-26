package com.kitri.cafe.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kitri.cafe.board.model.ReboardDto;

// root-context에 설정해둬서 맵핑 안해도 됨.
public interface ReboardDao {

	int writeArticle(ReboardDto reboardDto);
	List<ReboardDto> listArticle(Map<String, String> parameter);
	ReboardDto viewArticle(int seq);
	int modifyArticle(ReboardDto reboardDto);
	void deleteArticle(int seq);
	
}
