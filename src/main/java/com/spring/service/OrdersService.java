package com.spring.service;

import java.util.List;

import com.spring.dto.OrderVO;

public interface OrdersService {
	public List<OrderVO> selectList();
	public List<OrderVO> selectUserOrderList(OrderVO vo);
	public OrderVO selectUserOrderOne(OrderVO vo);
	public boolean insertOrder(OrderVO vo);
	public boolean updateOrderResult (OrderVO vo);
	public boolean deleteOrder (OrderVO vo);
}
