package com.spring.dao;

import java.util.List;

import com.spring.dto.CustomVO;

public interface CustomDAO {
	public int insertCustom(CustomVO vo);
	public List<CustomVO> selectCustomList();
}