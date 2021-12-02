package com.spring.dao;

import java.util.List;

import com.spring.dto.ReviewVO;

public interface ReviewDAO {
	public List<ReviewVO> selectList();
	public List<ReviewVO> selectMyReviews(ReviewVO vo);
	public List<ReviewVO> selectProductReviews(ReviewVO vo);
	public ReviewVO selectOne(ReviewVO vo);
	public int insertReview(ReviewVO vo);
	public int updateReview(ReviewVO vo);
	public int deleteReview(ReviewVO vo);
	public String selectImage(ReviewVO vo);
}
