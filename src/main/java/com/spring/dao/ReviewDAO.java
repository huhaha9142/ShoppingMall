package com.spring.dao;

import java.util.List;

import com.spring.dto.ReviewVO;

public interface ReviewDAO {
	public List<ReviewVO> selectList();
	public int insertReview(ReviewVO vo);
	
}
