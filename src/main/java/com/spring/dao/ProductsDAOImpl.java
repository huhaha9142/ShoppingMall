package com.spring.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class ProductsDAOImpl implements ProductsDAO {

	@Inject
    private SqlSession sqlSession;
	
	private static final String Namespace = "com.example.mapper.products";
	
	@Override
	public String selectImageString() {
		return sqlSession.selectOne(Namespace+".selectImage");
	}

}
