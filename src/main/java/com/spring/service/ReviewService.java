package com.spring.service;

import java.util.List;

import com.spring.dto.ReviewVO;

public interface ReviewService {
	public List<ReviewVO> selectList();
	public boolean insertReview(ReviewVO vo);
	public boolean updateReview(ReviewVO vo);
	public boolean deleteReview(ReviewVO vo);
}
