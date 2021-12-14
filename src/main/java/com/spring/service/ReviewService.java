package com.spring.service;

import java.util.List;

import com.spring.dto.ReviewVO;

public interface ReviewService {
	public List<ReviewVO> selectList();
	public List<ReviewVO> selectMyReviews(ReviewVO vo);
	public List<ReviewVO> selectProductReviews(ReviewVO vo);
	public ReviewVO selectOne(ReviewVO vo);
	public boolean insertReview(ReviewVO vo);
	public boolean updateReview(ReviewVO vo);
	public boolean deleteReview(ReviewVO vo);
	public String selectImage(ReviewVO vo);
}
