package com.kitri.cafe.board.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kitri.cafe.board.model.ReboardDto;
import com.kitri.cafe.board.service.ReboardService;
import com.kitri.cafe.common.service.CommonService;
import com.kitri.cafe.member.model.MemberDto;
import com.kitri.cafe.util.PageNavigation;

@Controller
@RequestMapping("/reboard")
public class ReboardController {
	
	@Autowired
	private ReboardService reboardService;
	
	@Autowired
	private CommonService commonService;
	
	// 단순페이징 이동
	@RequestMapping(value="/write",method = RequestMethod.GET)
	public void write(@RequestParam Map<String, String> parameter, Model model) { // @RequestParam 설정해줘야 맵에 다 들어감. 
		//write?bcode=${board.bcode}&pg=1&key=&word=  가지고 다녀야 하는 이유는 계속 그 게시판을 유지해야뎀 안하면 실행 안뎀.
		
		model.addAttribute("parameter", parameter);
		
		//return type이 void이면 /reboard/write가 저절로 적용뎀. return안해도됨.
	}
	
	// reboard 글쓰기
	@RequestMapping(value="/write",method = RequestMethod.POST)
	public String write(ReboardDto reboardDto,@RequestParam Map<String, String> parameter,
			Model model, HttpSession session) { // @RequestParam 설정해줘야 맵에 다 들어감. 
		//write?bcode=${board.bcode}&pg=1&key=&word=  가지고 다녀야 하는 이유는 계속 그 게시판을 유지해야뎀 안하면 실행 안뎀.
		
		String path = "";
		
		MemberDto memberDto = (MemberDto)session.getAttribute("userInfo");
		if (memberDto != null) {
			int seq = commonService.getNextSeq();
			//System.out.println(">>>>>>>>>>>>>>>>>>>>seq : " + seq );
			reboardDto.setSeq(seq);
			reboardDto.setId(memberDto.getId());
			reboardDto.setName(memberDto.getName());
			reboardDto.setEmail(memberDto.getEmail());
			reboardDto.setRef(seq); // 그룹번호 - 글번호를 그룹핑함.
			
			seq = reboardService.writeArticle(reboardDto);
			
			if (seq != 0) {
				model.addAttribute("seq",seq);
				path = "reboard/writeok";
			} else {
				path = "reboard/writefail";
			}
			
		} else {
			path = "";
		}
		model.addAttribute("parameter", parameter); // 실패하든 성공하든 가지고 다녀야 됨.
		return path;
	}
	
	 
	// 단순페이징 이동 / 글번호는 따로받음?
	@RequestMapping(value="/view",method = RequestMethod.GET)
	public String view(@RequestParam("seq") int seq, @RequestParam Map<String, String> parameter, 
			Model model, HttpSession session) { 
		
		MemberDto memberDto = (MemberDto)session.getAttribute("userInfo");
		String path = "";
		
		// if문으로 로그인 했는지 안했는지 체크하기
		if (memberDto != null) {
			ReboardDto reboardDto = reboardService.viewArticle(seq);
		
			model.addAttribute("article", reboardDto);
			model.addAttribute("parameter", parameter);
			path = "reboard/view";
		
		} else {
			path = "redirect:/index.jsp";
		}
		
		return path;
	}

	
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public void list(@RequestParam Map<String, String> parameter, Model model, HttpServletRequest request) { // @RequestParam 설정해줘야 맵에 다 들어감. 
		
		List<ReboardDto> list = reboardService.listArticle(parameter);
		PageNavigation pageNavigation = commonService.getPageNamigation(parameter); // 다른곳에서 다 사용하니 common으로 가야뎀
		pageNavigation.setRoot(request.getContextPath()); // ?
		pageNavigation.makeNavigator(); // 만들어진것을 가져가기
		
		model.addAttribute("parameter", parameter);
		model.addAttribute("articleList", list);
		model.addAttribute("navigator", pageNavigation);
		
		
	}
	
	// 댓글 단순페이지이동
	@RequestMapping(value="/reply",method = RequestMethod.GET)
	public String reply(@RequestParam("seq") int seq,
			@RequestParam Map<String, String> parameter, Model model, 
			HttpSession session) { // @RequestParam 설정해줘야 맵에 다 들어감. 
		
		String path = "";
		MemberDto memberDto = (MemberDto)session.getAttribute("userInfo");
		
		// if문으로 로그인 했는지 안했는지 체크하기
		if (memberDto != null) {
			ReboardDto reboardDto = reboardService.getArticle(seq);
		
			model.addAttribute("article", reboardDto);
			model.addAttribute("parameter", parameter);
			path = "reboard/reply";
		
		} else {
			path = "redirect:/index.jsp";
		}
		
		return path;
		
	}
	
	
	
	
	
	
	// reboard 글쓰기
	@RequestMapping(value="/reply",method = RequestMethod.POST)
	public String reply(ReboardDto reboardDto,@RequestParam Map<String, String> parameter,
			Model model, HttpSession session) { // @RequestParam 설정해줘야 맵에 다 들어감. 
		//write?bcode=${board.bcode}&pg=1&key=&word=  가지고 다녀야 하는 이유는 계속 그 게시판을 유지해야뎀 안하면 실행 안뎀.
		
		String path = "";
		
		MemberDto memberDto = (MemberDto)session.getAttribute("userInfo");
		if (memberDto != null) {
			int seq = commonService.getNextSeq();
			//System.out.println(">>>>>>>>>>>>>>>>>>>>seq : " + seq );
			reboardDto.setSeq(seq);
			reboardDto.setId(memberDto.getId());
			reboardDto.setName(memberDto.getName());
			reboardDto.setEmail(memberDto.getEmail());
			// 이미 원글것이 들어가있음 - ref가? seq가?
//			reboardDto.setRef(seq); // 그룹번호 - 글번호를 그룹핑함.
			
			seq = reboardService.replyArticle(reboardDto);
			
			if (seq != 0) {
				model.addAttribute("seq",seq);
				path = "reboard/writeok";
			} else {
				path = "reboard/writefail";
			}
			
		} else {
			path = "";
		}
		model.addAttribute("parameter", parameter); // 실패하든 성공하든 가지고 다녀야 됨.
		return path;
	}
	
	
	
		
}

















