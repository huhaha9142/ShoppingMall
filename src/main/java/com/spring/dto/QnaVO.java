package com.spring.dto;

import java.util.Date;

/*lombok 오픈소스 라이브러리
장점 : 코드를 간결하게 함 / 반복되는 코드 자동화
ex ) @Getter @Setter @toString*/

public class QnaVO {
	private String content;
	private String title;
	private Date inDate;
	private Long state;
	private Long usersNumber;
	private Long qnaNumber;
	private Date regDate;
	private String image;
	private String id;
	private String name;
	
	
	@Override
	public String toString() {
		return "QnaVO [content=" + content + ", title=" + title + ", inDate=" + inDate + ", state=" + state
				+ ", usersNumber=" + usersNumber + ", qnaNumber=" + qnaNumber + ", regDate=" + regDate + ", image="
				+ image + ", id=" + id + ", name=" + name + "]";
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
	
	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
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


	
	
	
	
}
