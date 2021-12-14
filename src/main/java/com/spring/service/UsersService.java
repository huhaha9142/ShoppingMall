package com.spring.service;

<<<<<<< HEAD
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

=======
import com.spring.dto.UsersVO;

public interface UsersService {
	public UsersVO selectLogin(UsersVO vo);
	public UsersVO selectLoginKakao(UsersVO vo);
	public int selectIdCheck(UsersVO vo);
	public UsersVO selectUserPrivacy(UsersVO vo);
	public UsersVO selectUserRule(UsersVO vo);
	public boolean insertUser(UsersVO vo);
	public boolean insertUserSocial(UsersVO vo);
	public boolean updateUserPrivacy(UsersVO vo);
	public boolean updatePassword(UsersVO vo);
	public boolean updateRuleByEmail(UsersVO vo);
	public boolean updateRulePassword(UsersVO vo);
	public boolean updatePasswordByEmail(UsersVO vo);
}
>>>>>>> main
