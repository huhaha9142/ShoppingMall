package com.spring.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.dto.AnswerVO;

@Repository
public class AnswerDAOImpl implements AnswerDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String Namespace = "com.example.mapper.answerMapper";
	
	@Override
	public List<AnswerVO> selectListAnswer(AnswerVO vo) {
		return sqlSession.selectList(Namespace + ".selectListAnswer", vo);
	}

	@Override
	public int insertAnswer(AnswerVO vo) {
		return sqlSession.insert(Namespace + ".insertAnswer", vo);
	}

	@Override
	public int updateAnswer(AnswerVO vo) {
		return sqlSession.update(Namespace + ".updateAnswer", vo);
	}

	@Override
	public int deleteAnswer(AnswerVO vo) {
		return sqlSession.delete(Namespace + ".deleteAnswer", vo);
	}

	@Override
	public String selectImage(AnswerVO vo) {
		return sqlSession.selectOne(Namespace + ".selectImage", vo);
	}

}
