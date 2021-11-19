package com.spring.dao;

import com.spring.dto.UsersVO;

public interface UsersDAO {
	public UsersVO selectLogin(UsersVO vo);
	public int selectIdCheck(UsersVO vo);
	
	public int insertUser(UsersVO vo);
	public int updateUserPrivacy(UsersVO vo);
	public int updatePassword(UsersVO vo);
	
}
