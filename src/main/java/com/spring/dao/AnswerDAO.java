package com.spring.dao;

import java.util.List;

import com.spring.dto.AnswerVO;

public interface AnswerDAO {
	public List<AnswerVO> selectListAnswer(AnswerVO vo);
	public int insertAnswer(AnswerVO vo);
	public int updateAnswer(AnswerVO vo);
	public int deleteAnswer(AnswerVO vo);
	public String selectImage(AnswerVO vo);
}
