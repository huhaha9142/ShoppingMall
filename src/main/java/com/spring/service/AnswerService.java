package com.spring.service;

import java.util.List;

import com.spring.dto.AnswerVO;


public interface AnswerService {

	public List<AnswerVO> selectListAnswer(AnswerVO vo);
	public boolean insertAnswer(AnswerVO vo);
	public boolean updateAnswer(AnswerVO vo);
	public boolean deleteAnswer(AnswerVO vo);
	public String selectImage(AnswerVO vo);
}
