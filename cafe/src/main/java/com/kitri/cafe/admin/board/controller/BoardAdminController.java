package com.kitri.cafe.admin.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kitri.cafe.admin.board.model.BoardListDto;
import com.kitri.cafe.admin.board.service.BoardAdminService;

@Controller
@RequestMapping("/boardadmin")
public class BoardAdminController {

	@Autowired // servlet-context에서 설정해 두어서 자동 주입됨.
	private BoardAdminService boardAdminService;
	
	@RequestMapping("/boardmenu")
	public ModelAndView boardMenu(@RequestParam(name = "ccode", defaultValue = "0") int ccode) {
		// service가 가지고있는 getBoardMenuList을 불러옴
		List<BoardListDto> list = boardAdminService.getBoardMenuList(ccode);
		System.out.println(list.size());
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardmenu",list);
		mav.setViewName("admin/boardmenu");
		return mav;
	}
	
}
