package com.spring.dto;

import java.util.Date;

public class UsersVO {
<<<<<<< HEAD
	private Long usersNumber;
	private String id;
	private String password;
	private String name;
	private String address;
	private String phone;
	private Date regDate;
	
	
	public Long getUsersNumber() {
		return usersNumber;
	}
	public void setUsersNumber(Long usersNumber) {
		this.usersNumber = usersNumber;
=======
	private Long userNumber;
	private String id;
	private String password;
	private String name;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String phone;
	private Date regDate;
	private Date inDate;
	private String nickName;
	private String rule;
	private String kakao;
	
	public String getKakao() {
		return kakao;
	}

	public void setKakao(String kakao) {
		this.kakao = kakao;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Date getInDate() {
		return inDate;
	}

	public void setInDate(Date inDate) {
		this.inDate = inDate;
	}

	public UsersVO(){}
	
	public UsersVO(String id,String password)
	{
		this.id = id;
		this.password = password;
	}
	
	public Long getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(Long userNumber) {
		this.userNumber = userNumber;
>>>>>>> main
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
<<<<<<< HEAD
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
=======
>>>>>>> main
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
<<<<<<< HEAD
	@Override
	public String toString() {
		return "UserVO [usersNumber=" + usersNumber + ", id=" + id + ", password=" + password + ", name=" + name
				+ ", address=" + address + ", phone=" + phone + ", regDate=" + regDate + "]";
	}
	
}
=======
	
}
>>>>>>> main
