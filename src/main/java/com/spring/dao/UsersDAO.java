package com.spring.dao;

import com.spring.dto.UsersVO;

public interface UsersDAO {

	//	회원가입
	public void userJoin(UsersVO vo) throws Exception;
	
	//	로그인
	public UsersVO userLogin(UsersVO vo) throws Exception;
}
