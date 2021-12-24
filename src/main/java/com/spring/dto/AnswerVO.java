package com.spring.dto;

import java.util.Date;

public class AnswerVO {
	private String title;
	private String content;
	private Date inDate;
	private Date regDate;
	private String image;
	private Long qnaNumber;
	private Long answerNumber;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getInDate() {
		return inDate;
	}
	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getQnaNumber() {
		return qnaNumber;
	}
	public void setQnaNumber(Long qnaNumber) {
		this.qnaNumber = qnaNumber;
	}
	public Long getAnswerNumber() {
		return answerNumber;
	}
	public void setAnswerNumber(Long answerNumber) {
		this.answerNumber = answerNumber;
	}
	
	@Override
	public String toString() {
		return "AnswerVO [title=" + title + ", content=" + content + ", inDate=" + inDate + ", regDate=" + regDate
				+ ", image=" + image + ", qnaNumber=" + qnaNumber + ", answerNumber=" + answerNumber + "]";
	}
	

}
