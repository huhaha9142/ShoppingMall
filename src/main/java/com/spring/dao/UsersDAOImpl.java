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

}
