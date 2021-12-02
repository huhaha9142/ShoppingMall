package com.spring.dao;

import java.util.List;

import com.spring.dto.OrderVO;

public interface OrdersDAO {
	public List<OrderVO> selectList();
	public List<OrderVO> selectUserOrderList(OrderVO vo);
	public OrderVO selectUserOrderOne(OrderVO vo);
	public int insertOrder(OrderVO vo);
	public int updateOrderResult (OrderVO vo);
	public int deleteOrder (OrderVO vo);

}
