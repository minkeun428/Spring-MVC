package com.spring.mvc.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.spring.mvc.board.dao.BoardDao;
import com.spring.mvc.board.domain.BoardVO;


@Service
public class BoardServiceImpl implements BoardService {
	
	//java9부터 자바 어노테이션을 지원하지 않기 때문에, maven 따로 설정 추가해야 함.
	@Resource
	private BoardDao boardDao;
	
	public BoardDao getBoardDao() {
		return boardDao;
	}
	
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	
	@Override
	public List<BoardVO> list() {
		
		return boardDao.list();
	}

	@Override
	public int delete(BoardVO boardVO) {
		
		return boardDao.delete(boardVO);
	}

	@Override
	public int edit(BoardVO boardVO) {
		return boardDao.update(boardVO);
	}

	@Override
	public void write(BoardVO boardVO) {
		boardDao.insert(boardVO);
		
	}

	@Override
	public BoardVO read(int seq) {
		boardDao.updateReadCount(seq);
		
		return boardDao.select(seq);
	}

}
