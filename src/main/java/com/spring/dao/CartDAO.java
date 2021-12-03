package com.spring.dao;

import java.util.List;

import com.spring.dto.CartVO;

public interface CartDAO {
	public List<CartVO> selectListCart();
	public int insertCart(CartVO vo);
	public int updateCart(CartVO vo);
	public int deleteCart(CartVO vo);
	
}
