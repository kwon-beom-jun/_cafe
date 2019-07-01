package com.kitri.cafe.board.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kitri.cafe.board.model.MemoDto;
import com.kitri.cafe.board.service.MemoService;
import com.kitri.cafe.member.model.MemberDto;

//@Controller
@RestController // 무조건 @ResponseBody로 넘겨준다는 뜻이므로 @ResponseBody를 쓸 필요가 없음 form은 안뎀 get, post밖에 안뎀.
@RequestMapping("/memo")
public class MemoController {

	@Autowired
	private MemoService memoService;
	
												//  -------------------------- 넘겨줄때 똑같은것으로 지정한것 -------------------------
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json", headers = {"Content-type=application/json"})
	//public @ResponseBody String write(@RequestBody MemoDto memoDto, HttpSession session) {
	public String write(@RequestBody MemoDto memoDto, HttpSession session) {
		// (json일때) 다시 보낼땐 @ResponseBody view에서 받아올땐 @RequestBody
		
//		System.out.println(memoDto.getMcontent());
		
		MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");
		
		if (memberDto != null) {
			memoDto.setId(memberDto.getId());
			memoDto.setName(memberDto.getName());
			memoService.writeMemo(memoDto);
			
			String json = memoService.listMemo(memoDto.getSeq());
			
			return json; // @ResponseBody해서 응답의 바디로 감.
		}
		
		return "";
	}
	
	@RequestMapping(method = RequestMethod.GET, consumes = "application/json", headers = {"Content-type=application/json"})
	public String list(int seq) {
		String json = memoService.listMemo(seq);
		return json;
	}
	
//	/memo/m글번호 <- 값이 바뀌는 url = 변수 그냥 가져오는것이 아니라 @PathVariable를 설정해줘야뎀.
	@RequestMapping(value = "/{seq}/{mseq}", method = RequestMethod.DELETE, consumes = "application/json", headers = {"Content-type=application/json"})
	public String delete(@PathVariable(name="seq") int seq, @PathVariable(name="mseq") int mseq) {
		String json = memoService.deleteMemo(seq, mseq);
		return json;
	}
	
	
}

















