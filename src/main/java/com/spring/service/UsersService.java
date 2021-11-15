package com.spring.service;

import com.spring.dto.UsersVO;

public interface UsersService {
	public UsersVO selectLogin(UsersVO vo);
	public boolean insertUser(UsersVO vo);
}
