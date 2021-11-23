package com.spring.dto;

import java.util.Date;

/*lombok 오픈소스 라이브러리
장점 : 코드를 간결하게 함 / 반복되는 코드 자동화
ex ) @Getter @Setter @toString*/

public class QnaVO {
	private String content;
	private String title;
	private Date inDate;
	private Long hit; // state로 바꿔줘야함
	private Long usersNumber;
	private Long qnaNumber;
	
	@Override
	public String toString() {
		return "QnaVO [content=" + content + ", title=" + title + ", inDate=" + inDate + ", hit=" + hit
				+ ", usersNumber=" + usersNumber + ", qnaNumber=" + qnaNumber + "]";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public Long getHit() {
		return hit;
	}

	public void setHit(Long hit) {
		this.hit = hit;
	}

	public Long getUsersNumber() {
		return usersNumber;
	}

	public void setUsersNumber(Long usersNumber) {
		this.usersNumber = usersNumber;
	}

	public Long getQnaNumber() {
		return qnaNumber;
	}

	public void setQnaNumber(Long qnaNumber) {
		this.qnaNumber = qnaNumber;
	}
	
	
	
	
}
