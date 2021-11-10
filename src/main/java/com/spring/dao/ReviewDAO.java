package com.spring.dao;

import java.util.List;

import com.spring.dto.ReviewVO;

public interface ReviewDAO {
	public List<ReviewVO> selectList();
	public int insertReview(ReviewVO vo);
	public int updateReview(ReviewVO vo);
	public int deleteReview(ReviewVO vo);
	public String selectImage(ReviewVO vo);
}
