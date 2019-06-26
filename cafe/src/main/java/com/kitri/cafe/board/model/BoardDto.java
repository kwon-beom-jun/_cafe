package com.kitri.cafe.board.model;

public class BoardDto {

	// 자바일때 언더바는 무조건 상수만 나중에 알리아스 줌.
	
	private int seq;
	private String id;
	private String name;
	private String email;
	private String subject;
	private String content;
	private int hit;
	private String logtime;
	private int bcode;
	
	
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getLogtime() {
		return logtime;
	}
	public void setLogtime(String logtime) {
		this.logtime = logtime;
	}
	public int getBcode() {
		return bcode;
	}
	public void setBcode(int bcode) {
		this.bcode = bcode;
	}
	
	@Override
	public String toString() {
		return "BoardDto [seq=" + seq + ", id=" + id + ", name=" + name + ", email=" + email + ", subject=" + subject
				+ ", content=" + content + ", hit=" + hit + ", logtime=" + logtime + ", bcode=" + bcode + "]";
	}
	
	
	
	
	
}
