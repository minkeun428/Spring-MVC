package com.spring.mvc.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.mvc.board.domain.BoardVO;
import com.spring.mvc.board.service.BoardService;


// 모델을 생성하는 것은 DispatcherServlet의 역할.
// DispatcherServlet이 생성한 모델에 대한 참조 변수는
// @RequestMapping 어노테이션이 붙은 메서드에서 
// 인자를 선언하기만 하면 자동으로 받을 수 있다.

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping(value="/board/list")
  //@ResponseBody   반환하는 문자열을 그대로 웹 브라우저에게 응답함.
	public String list(Model model) {
		//return boardService.list().toString();
		
		model.addAttribute("boardList", boardService.list());
		
		return "/board/list";
	}
	
	@RequestMapping(value="/board/read/{seq}")
	//{seq}처럼 경로 변수를 메서드의 인자로 사용하려면 @PathVariable 어노테이션이 필요함.
	//기존에 request.getParameter("seq")를 다시 Int형으로 변환하는 지루한 작업을 
	//스프링 MVC가 대신 처리해주는 것.
	public String read(Model model, @PathVariable int seq) {
		model.addAttribute("boardVO", boardService.read(seq));
		
		return "/board/read";
	}
	
	@RequestMapping(value="/board/write", method=RequestMethod.GET)
	public String write() {
		
		return "/board/write";
	}
	
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String write(BoardVO boardVO) {
		
		boardService.write(boardVO);
		
		return "/board/write";
	}

}
