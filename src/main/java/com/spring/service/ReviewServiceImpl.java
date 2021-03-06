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
		return dao.selectList();
	}
	@Override
	public boolean insertReview(ReviewVO vo) {
		return 1==dao.insertReview(vo);
	}
	@Override
	public boolean updateReview(ReviewVO vo) {
		return 1==dao.updateReview(vo);
	}
	@Override
	public boolean deleteReview(ReviewVO vo) {
		return 1==dao.deleteReview(vo);
	}
	@Override
	public String selectImage(ReviewVO vo) {
		return dao.selectImage(vo);
	}
	@Override
	public List<ReviewVO> selectMyReviews(ReviewVO vo) {
		return dao.selectMyReviews(vo);
	}
	@Override
	public List<ReviewVO> selectProductReviews(ReviewVO vo) {	
		return dao.selectProductReviews(vo);
	}
	@Override
	public ReviewVO selectOne(ReviewVO vo) {
		// TODO Auto-generated method stub
		return dao.selectOne(vo);
	}


}
