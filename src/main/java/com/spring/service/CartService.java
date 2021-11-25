package com.spring.service;

import java.util.List;

import com.spring.dto.CartVO;

public interface CartService {
	public List<CartVO> selectListCart();
	public List<CartVO> selectUserOneCart();
	public List<CartVO> selectUserQuanCart();
	public boolean insertCart(CartVO vo);
	public boolean updateCart(CartVO vo);
	public boolean deleteCart(CartVO vo);
}
