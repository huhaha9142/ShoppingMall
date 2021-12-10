package com.spring.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.dto.OrderVO;

@Repository
public class OrderDAOImpl implements OrdersDAO {


    @Inject
    private SqlSession sqlSession;
    private static final String Namespace = "com.example.mapper.ordersMapper";
	@Override
	public List<OrderVO> selectList() {
		
		return sqlSession.selectList(Namespace+".selectList");
	}

	@Override
	public List<OrderVO> selectUserOrderList(OrderVO vo) {
		
		return sqlSession.selectList(Namespace+".selectUserOrderList", vo);
	}

	@Override
	public OrderVO selectUserOrderOne(OrderVO vo) {
		
		return sqlSession.selectOne(Namespace+".selectUserOrderOne", vo);
	}

	@Override
	public int insertOrder(OrderVO vo) {
		
		return sqlSession.insert(Namespace+".insertOrder", vo);
	}

	@Override
	public int updateOrderResult(OrderVO vo) {
		
		return sqlSession.update(Namespace+".updateOrderResult", vo);
	}

	@Override
	public int deleteOrder(OrderVO vo) {
		
		return sqlSession.delete(Namespace+".deleteOrder", vo);
	}


	@Override
	public int updateOrderTid(OrderVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.update(Namespace+".updateOrderTid", vo);
	}

	@Override
	public List<OrderVO> selectOrderTid(OrderVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".selectOrderTid", vo);
	}

	@Override
	public int updateOrderResultByUuid(OrderVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.update(Namespace+".updateOrderResultByUuid", vo);
	}

}
