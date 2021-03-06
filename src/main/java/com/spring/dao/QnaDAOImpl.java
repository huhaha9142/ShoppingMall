package com.spring.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.dto.QnaVO;

@Repository
public class QnaDAOImpl implements QnaDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String Namespace = "com.example.mapper.qnaMapper";
	
	@Override
	public List<QnaVO> selectListQna() {
		return sqlSession.selectList(Namespace + ".selectListQna");
	}
	@Override
	public List<QnaVO> selectListUserQna(QnaVO vo) {
		return sqlSession.selectList(Namespace + ".selectListUserQna", vo);
	}


	@Override
	public int insertQna(QnaVO vo) {
		return sqlSession.insert(Namespace + ".insertQna", vo);
	}

	@Override
	public int updateQna(QnaVO vo) {
		return sqlSession.update(Namespace + ".updateQna", vo);
	}

	@Override
	public int deleteQna(QnaVO vo) {
		return sqlSession.delete(Namespace + ".deleteQna", vo);
	}
	@Override
	public String selectImage(QnaVO vo) {
		return sqlSession.selectOne(Namespace + ".selectImage", vo);
	}








}
