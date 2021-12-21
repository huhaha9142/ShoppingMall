package com.spring.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.dto.CartVO;

@Repository
public class CartDAOImpl implements CartDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String Namespace = "com.example.mapper.cartMapper";
	
	@Override
	public List<CartVO> selectListCart(CartVO vo) {
		
		return sqlSession.selectList(Namespace+".selectListCart",vo);
	}

	@Override
	public int insertCart(CartVO vo) {
		
		return sqlSession.insert(Namespace+".insertCart", vo);
	}

	@Override
	public int updateCart(CartVO vo) {
		
		return sqlSession.update(Namespace+".updateCart", vo);
	}

	@Override
	public int deleteCart(CartVO vo) {
		
		return sqlSession.delete(Namespace+".deleteCart", vo);
	}

}
