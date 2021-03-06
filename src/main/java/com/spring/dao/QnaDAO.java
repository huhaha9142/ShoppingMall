package com.spring.dao;

import java.util.List;

import com.spring.dto.QnaVO;

public interface QnaDAO {
	public List<QnaVO> selectListQna();
	public List<QnaVO> selectListUserQna(QnaVO vo);
	public int insertQna(QnaVO vo);
	public int updateQna(QnaVO vo);
	public int deleteQna(QnaVO vo);
	public String selectImage(QnaVO vo);
}
