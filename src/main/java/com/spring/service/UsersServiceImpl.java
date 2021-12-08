package com.spring.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring.dao.UsersDAO;
import com.spring.dto.UsersVO;

@Service
public class UsersServiceImpl implements UsersService {

	@Inject
	private UsersDAO dao;
	@Override
	public UsersVO selectLogin(UsersVO vo) {
		// TODO Auto-generated method stub
		return dao.selectLogin(vo);
	}

	@Override
	public boolean insertUser(UsersVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.insertUser(vo);
	}

	@Override
	public int selectIdCheck(UsersVO vo) {
		// TODO Auto-generated method stub
		return dao.selectIdCheck(vo);
	}

	@Override
	public boolean updateUserPrivacy(UsersVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.updateUserPrivacy(vo);
	}

	@Override
	public boolean updatePassword(UsersVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.updatePassword(vo);
	}

	@Override
	public UsersVO selectUserPrivacy(UsersVO vo) {
		// TODO Auto-generated method stub
		return dao.selectUserPrivacy(vo);
	}

	@Override
	public boolean updateRuleByEmail(UsersVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.updateRuleByEmail(vo);
	}

	@Override
	public boolean updateRulePassword(UsersVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.updateRulePassword(vo);
	}

	@Override
	public boolean updatePasswordByEmail(UsersVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.updatePasswordByEmail(vo);
	}

	@Override
	public UsersVO selectUserRule(UsersVO vo) {
		// TODO Auto-generated method stub
		return dao.selectUserRule(vo);
	}

	@Override
	public UsersVO selectLoginKakao(UsersVO vo) {
		// TODO Auto-generated method stub
		return dao.selectLoginKakao(vo);
	}

	@Override
	public boolean insertUserSocial(UsersVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.insertUserSocial(vo);
	}

}
