package com.spring.service;

import com.spring.dto.UsersVO;

public interface UsersService {
	public UsersVO selectLogin(UsersVO vo);
	public int selectIdCheck(UsersVO vo);
	public boolean insertUser(UsersVO vo);
	public boolean updateUserPrivacy(UsersVO vo);
	public boolean updatePassword(UsersVO vo);
}
