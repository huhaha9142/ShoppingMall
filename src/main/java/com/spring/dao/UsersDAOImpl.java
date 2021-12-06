package com.spring.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.dto.UsersVO;

@Repository
public class UsersDAOImpl implements UsersDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String Namespace = "com.example.mapper.usersMapper";
	
	@Override
	public UsersVO selectLogin(UsersVO vo) {
		
		return sqlSession.selectOne(Namespace+".selectLogin", vo);
	}

	@Override
	public int insertUser(UsersVO vo) {
	
		return sqlSession.insert(Namespace+".insertUser", vo);
	}

	@Override
	public int selectIdCheck(UsersVO vo) {
		
		return sqlSession.selectOne(Namespace+".selectIdCheck", vo);
	}

	@Override
	public int updateUserPrivacy(UsersVO vo) {
		
		return sqlSession.update(Namespace+".updateUserPrivacy", vo);
	}

	@Override
	public int updatePassword(UsersVO vo) {
		
		return sqlSession.update(Namespace+".updatePassword", vo);
	}

	@Override
	public UsersVO selectUserPrivacy(UsersVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectUserPrivacy", vo);
	}

	@Override
	public int updateRuleByEmail(UsersVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.update(Namespace+".updateRuleByEmail", vo);
	}

	@Override
	public int updateRulePassword(UsersVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.update(Namespace+".updateRulePassword", vo);
	}

	@Override
	public int updatePasswordByEmail(UsersVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.update(Namespace+".updatePasswordByEmail", vo);
	}

	@Override
	public UsersVO selectUserRule(UsersVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectUserRule", vo);
	}

	@Override
	public UsersVO selectLoginKakao(UsersVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectLoginKakao",vo);
	}

}
