package com.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring.dao.QnaDAO;
import com.spring.dto.QnaVO;

@Service
public class QnaServiceImpl implements QnaService {

	@Inject
	private QnaDAO dao;
	@Override
	public List<QnaVO> selectListQna() {
		return dao.selectListQna();
	}
	
	@Override
	public List<QnaVO> selectListUserQna(QnaVO vo) {
		return dao.selectListUserQna(vo);
	}

	@Override
	public boolean insertQna(QnaVO vo) {
		return 1==dao.insertQna(vo);
	}

	@Override
	public boolean updateQna(QnaVO vo) {
		
		return 1==dao.updateQna(vo);
	}

	@Override
	public boolean deleteQna(QnaVO vo) {
		
		return 1==dao.deleteQna(vo);
	}

	@Override
	public String selectImage(QnaVO vo) {
		
		return dao.selectImage(vo);
	}



}
