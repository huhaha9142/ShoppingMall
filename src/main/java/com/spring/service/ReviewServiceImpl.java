package com.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring.dao.ReviewDAO;
import com.spring.dto.ReviewVO;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Inject
	private ReviewDAO dao;
	@Override
	public List<ReviewVO> selectList() {
		// TODO Auto-generated method stub
		return dao.selectList();
	}
	@Override
	public boolean insertReview(ReviewVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.insertReview(vo);
	}

}
