package com.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring.dao.OrdersDAO;
import com.spring.dto.OrderVO;

@Service
public class OrdersServiceImpl implements OrdersService {

	@Inject
	private OrdersDAO dao;
	@Override
	public List<OrderVO> selectList() {
		
		return dao.selectList();
	}

	@Override
	public List<OrderVO> selectUserOrderList(OrderVO vo) {
		
		return dao.selectUserOrderList(vo);
	}

	@Override
	public OrderVO selectUserOrderOne(OrderVO vo) {
		
		return dao.selectUserOrderOne(vo);
	}

	@Override
	public boolean insertOrder(OrderVO vo) {
		
		return 1==dao.insertOrder(vo);
	}

	@Override
	public boolean updateOrderResult(OrderVO vo) {
		
		return 1==dao.updateOrderResult(vo);
	}

	@Override
	public boolean deleteOrder(OrderVO vo) {
		
		return 1==dao.deleteOrder(vo);
	}

	@Override
	public boolean updateOrderTid(OrderVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.updateOrderTid(vo);
	}

	@Override
	public List<OrderVO> selectOrderTid(OrderVO vo) {
		// TODO Auto-generated method stub
		return dao.selectOrderTid(vo);
	}

	@Override
	public boolean updateOrderResultByUuid(OrderVO vo) {
		// TODO Auto-generated method stub
		return 0<dao.updateOrderResultByUuid(vo);
	}

}
