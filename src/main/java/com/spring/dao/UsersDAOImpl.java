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
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectLogin", vo);
	}

	@Override
	public int insertUser(UsersVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.insert(Namespace+".insertUser", vo);
	}

}
