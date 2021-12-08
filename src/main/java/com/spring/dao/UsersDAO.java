package com.spring.dao;

import com.spring.dto.UsersVO;

public interface UsersDAO {
	public UsersVO selectLogin(UsersVO vo);
	public UsersVO selectLoginKakao(UsersVO vo);
	public int selectIdCheck(UsersVO vo);
	public UsersVO selectUserPrivacy(UsersVO vo);
	public UsersVO selectUserRule(UsersVO vo);
	public int insertUser(UsersVO vo);
	public int insertUserSocial(UsersVO vo);
	public int updateUserPrivacy(UsersVO vo);
	public int updatePassword(UsersVO vo);
	public int updateRuleByEmail(UsersVO vo);
	public int updateRulePassword(UsersVO vo);
	public int updatePasswordByEmail(UsersVO vo);
	
}
