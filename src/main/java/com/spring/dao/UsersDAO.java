package com.spring.dao;

import com.spring.dto.UsersVO;

public interface UsersDAO {
	public UsersVO selectLogin(UsersVO vo);
	public int insertUser(UsersVO vo);
	
}
