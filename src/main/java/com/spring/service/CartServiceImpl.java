package com.spring.service;


import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring.dao.CartDAO;
import com.spring.dto.CartVO;
@Service
public class CartServiceImpl implements CartService {

	@Inject
	private CartDAO dao;
	@Override
	public List<CartVO> selectListCart(CartVO vo) {
		
		return dao.selectListCart(vo);
	}

	@Override
	public boolean insertCart(CartVO vo) {
		
		return 1==dao.insertCart(vo);
	}

	@Override
	public boolean updateCart(CartVO vo) {
		
		return 1==dao.updateCart(vo);
		
	}

	@Override
	public boolean deleteCart(CartVO vo) {
		
		return 1==dao.deleteCart(vo);
	}

}
