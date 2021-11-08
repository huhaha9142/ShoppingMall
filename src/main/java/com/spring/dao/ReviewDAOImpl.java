package com.spring.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.dto.ReviewVO;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

	@Inject
    private SqlSession sqlSession;
	private static final String Namespace = "com.example.mapper.reviewMapper";
	@Override
	public List<ReviewVO> selectList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".selectListReview");
	}
	@Override
	public int insertReview(ReviewVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.insert(Namespace+".insertReview",vo);
	}
	@Override
	public int updateReview(ReviewVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.update(Namespace+".updateReview", vo);
	}
	@Override
	public int deleteReview(ReviewVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.delete(Namespace+".deleteReview", vo);
	}

}
