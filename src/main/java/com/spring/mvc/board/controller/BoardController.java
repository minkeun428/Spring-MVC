package com.spring.mvc.board.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.spring.mvc.board.domain.BoardVO;
import com.spring.mvc.board.service.BoardService;


// 모델을 생성하는 것은 DispatcherServlet의 역할.
// DispatcherServlet이 생성한 모델에 대한 참조 변수는
// @RequestMapping 어노테이션이 붙은 메서드에서 
// 인자를 선언하기만 하면 자동으로 받을 수 있다.

@Controller
@SessionAttributes("boardVO")   //boardVO가 MVC 모델에 추가될 때, 세션에도 boardVO를 저장하라.
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
	public String write(Model model) {
		
		model.addAttribute("boardVO", new BoardVO());
		
		return "/board/write";
	}
	
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	// Hibernate-Validator사용을 위한 @Valid추가
	// 이메일 주소 형식, 신용카드 형식, 안전한HTML인지 검증 기능 제공
	public String write(@Valid BoardVO boardVO, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "/board/write";
		} 
		else {
			boardService.write(boardVO);
			
			return "redirect:/board/list";
		}
		
	}
	
	@RequestMapping(value="/board/edit/{seq}", method=RequestMethod.GET)
	public String edit(@PathVariable int seq, Model model) {
		
		BoardVO boardVO = boardService.read(seq);
		
		//boardVO를 model에 넣으면, 
		//@SessionAttributes에 의해 자동으로 세션에 추가됨.
		model.addAttribute("boardVO", boardVO);   
		
		return "/board/edit";
	}
	
	//세션에서 저장한 객체를 활용하는 edit() 인자에 @ModelAttribute 지정.
	//@SessionAttributes에서 지정된 객체를 먼저 바인딩함.
	@RequestMapping(value="/board/edit/{seq}", method=RequestMethod.POST)
	public String edit(@Valid @ModelAttribute BoardVO boardVO,
			BindingResult result, int pwd, SessionStatus sessionStatus, Model model) {
		
		if(result.hasErrors()) {
			return "/board/edit";
		}
		else {
			if(boardVO.getPassword() == pwd) {
				
				boardService.edit(boardVO);
				sessionStatus.setComplete();  //@SessionAttribute에 의해 세션에 저장된 객체를 제거.
				
				return "redirect:/board/list";
			}
			
			model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
			return "/board/edit";
		}
		
		
	}

}
