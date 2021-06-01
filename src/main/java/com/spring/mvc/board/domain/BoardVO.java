package com.spring.mvc.board.domain;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

@Alias("boardVO")
public class BoardVO {
	
	private int seq;
	private String title;
	private String content;
	private String writer;
	private int password;
	private Timestamp regDate;
	private int cnt;
	
	public BoardVO() {}
	
	public BoardVO (String title, String content, String writer, int password) {
		super();
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.password = password;
		this.cnt = 0;
	}

}
