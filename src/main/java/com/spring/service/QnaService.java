package com.spring.service;

import java.util.List;

import com.spring.dto.QnaVO;

public interface QnaService {

	public List<QnaVO> selectListQna();
	public boolean insertQna(QnaVO vo);
	public boolean updateQna(QnaVO vo);
	public boolean deleteQna(QnaVO vo);
}
