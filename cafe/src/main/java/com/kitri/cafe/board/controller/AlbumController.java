package com.kitri.cafe.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kitri.cafe.board.model.AlbumDto;
import com.kitri.cafe.board.model.ReboardDto;
import com.kitri.cafe.board.service.AlbumService;
import com.kitri.cafe.common.service.CommonService;
import com.kitri.cafe.member.model.MemberDto;

@Controller
@RequestMapping("/album")
public class AlbumController {
	
	@Autowired
	private ServletContext servletContext; // 내장객체임. 톰캣이 만들어놨음.
	@Autowired
	private AlbumService albumService;
	@Autowired
	private CommonService commonService;
	
	// 단순페이징 이동
	@RequestMapping(value="/write",method = RequestMethod.GET)
	public void write(@RequestParam Map<String, String> parameter, Model model) { // @RequestParam 설정해줘야 맵에 다 들어감. 
		//write?bcode=${board.bcode}&pg=1&key=&word=  가지고 다녀야 하는 이유는 계속 그 게시판을 유지해야뎀 안하면 실행 안뎀.
		
		model.addAttribute("parameter", parameter);
		
		//return type이 void이면 /reboard/write가 저절로 적용뎀.
	}


	// 파일 톰켓에서 DBCP - 데이타베이스 커넥션풀링씀. (톰캣 내부에 DBCP.jar를 가지고 있음.)
	// MultipartFile을 해야뎀. @RequestParam("picture") 이름 picutre으로 넘어옴.
	@RequestMapping(value="/write",method = RequestMethod.POST)
	public String write(AlbumDto albumDto, 
			@RequestParam("picture") MultipartFile multipartFile, // 사진 여러개 넣는 방법?
			@RequestParam Map<String, String> parameter,
			Model model, HttpSession session) { // @RequestParam 설정해줘야 맵에 다 들어감. 
		//write?bcode=${board.bcode}&pg=1&key=&word=  가지고 다녀야 하는 이유는 계속 그 게시판을 유지해야뎀 안하면 실행 안뎀.
		
		
		
		MemberDto memberDto = (MemberDto)session.getAttribute("userInfo");
		
		if (memberDto != null) {
			
			
			int seq = commonService.getNextSeq();
			//System.out.println(">>>>>>>>>>>>>>>>>>>>seq : " + seq );
			albumDto.setSeq(seq);
			albumDto.setId(memberDto.getId());
			albumDto.setName(memberDto.getName());
			albumDto.setEmail(memberDto.getEmail());
			
			
			
			// 파일 셋팅 - 파일을 올렸다면
			if (multipartFile != null && !multipartFile.isEmpty()) {
				
				String orignPicture = multipartFile.getOriginalFilename();
				
				// context도 잡아줘야뎀. 파일을 처리하는것임.
				String realpath = servletContext.getRealPath("/upload/album");
//				System.out.println(realpath);
				
				DateFormat df = new SimpleDateFormat("yyMMdd"); // java는 소문자 mm이 분임.
				String saveFolder = df.format(new Date());
				
				// 파일 구분자 설정. File.pathSeparator = 윈도우, 유닉스 파일구분자 저절로 처리해줌.
				String realSaveFolder = realpath + File.separator + saveFolder;
//				System.out.println(realSaveFolder);
				
				// 파일 객체 생성
				File dir = new File(realSaveFolder);
				
				if (!dir.exists()) { // exists = 존재한다면
					dir.mkdirs(); // makedirectory 디렉토리를 만들어라.
				}
				
				// 파일 이름 안겹치게 UUID 사용. 유니버셜유니크아이디		 // 확장자를 붙여줌 마지막의 .을 가져와서 확장자를 붙임.
				String savePicture = UUID.randomUUID().toString() + orignPicture.substring(orignPicture.lastIndexOf('.'));
				
				// 파일을 임시저장소에 옮겨라?
				File file = new File(realSaveFolder, savePicture);
				
				try {
					multipartFile.transferTo(file);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				albumDto.setOrignPicture(orignPicture);
				albumDto.setSavePicture(savePicture);
				albumDto.setSaveFolder(saveFolder); // 웹에서 보일때는 전체경로 필요 없음. 폴더 이름만 필요
					
				}
				
			
//				TODO : 1. image file 검사 2. thumbnail image
			// if문 안에? 들어가있으면 사진올려야만 글작성 가능.
			seq = albumService.writeArticle(albumDto);
			
			if (seq != 0) {
				model.addAttribute("seq",seq);
			} else {
				model.addAttribute("errorMsg","서버문제로 글작성이 실패하였습니다.");
			}
			
		} else {
			model.addAttribute("errorMsg","로그인 후 글작성 하시오.");
		}
		
		model.addAttribute("parameter", parameter); // 실패하든 성공하든 가지고 다녀야 됨.
		return "album/writeok";
			
	}
		
	
}
