package com.spring.dao;

import java.util.List;

import com.spring.dto.CartVO;

public interface CartDAO {
	public List<CartVO> selectListCart();
	public List<CartVO> selectUserOneCart();
	public List<CartVO> selectUserQuanCart();
	public int insertCart(CartVO vo);
	public int updateCart(CartVO vo);
	public int deleteCart(CartVO vo);
	
}
