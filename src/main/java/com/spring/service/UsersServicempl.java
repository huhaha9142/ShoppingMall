package com.spring.service;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.spring.dao.UsersDAO;
import com.spring.dto.UsersVO;

public class UsersServicempl implements UsersService {

	@Inject
	private UsersDAO dao;
	
	
	//	회원가입
	@Override
	public void userJoin(UsersVO vo) throws Exception {
		dao.userJoin(vo);
		
	}

	//	로그인
	@Override
	public UsersVO userLogin(UsersVO vo) throws Exception {
		
		return dao.userLogin(vo);
	}

	//	로그아웃
	@Override
	public void userLogout(HttpSession session) throws Exception {
		session.invalidate();
		
	}

}
