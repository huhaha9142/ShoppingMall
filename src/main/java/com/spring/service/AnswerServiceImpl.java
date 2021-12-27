package com.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring.dao.AnswerDAO;
import com.spring.dto.AnswerVO;

@Service
public class AnswerServiceImpl implements AnswerService {

	@Inject
	private AnswerDAO dao;
	@Override
	public List<AnswerVO> selectListAnswer(AnswerVO vo) {
		return dao.selectListAnswer(vo);
	}

	@Override
	public boolean insertAnswer(AnswerVO vo) {
		return 1==dao.insertAnswer(vo);
	}

	@Override
	public boolean updateAnswer(AnswerVO vo) {
		return 1==dao.updateAnswer(vo);
	}

	@Override
	public boolean deleteAnswer(AnswerVO vo) {
		return 1==dao.deleteAnswer(vo);
	}

	@Override
	public String selectImage(AnswerVO vo) {
		return dao.selectImage(vo);
	}

}
