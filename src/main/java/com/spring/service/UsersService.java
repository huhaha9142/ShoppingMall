package com.spring.service;

import javax.servlet.http.HttpSession;

import com.spring.dto.UsersVO;

public interface UsersService {

	
	//	회원가입
	public void userJoin(UsersVO vo) throws Exception;
	
	//	로그인
	public UsersVO userLogin(UsersVO vo) throws Exception;
	
	//	로그아웃
	public void userLogout(HttpSession session) throws Exception;
}

