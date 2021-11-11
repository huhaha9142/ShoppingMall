package com.spring.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;

import com.spring.dto.UsersVO;

public class UsersDAOImpl implements UsersDAO {

	@Inject
	private SqlSession sqlSession;
	
	// 매퍼
	private static final String namespace = "com.example.mappers.userMapper";
	
	//	회원가입
	@Override
	public void userJoin(UsersVO vo) throws Exception {
		sqlSession.insert(namespace + ".userJoin", vo);
		
	}

	//	로그인
	@Override
	public UsersVO userLogin(UsersVO vo) throws Exception {
		
		return sqlSession.selectOne(namespace + ".userLogin", vo);
	}

}
