package com.spring.dao;

import java.util.List;

import com.spring.dto.CustomVO;

public interface CustomDAO {
	public int insertCustom(CustomVO vo);
	public List<CustomVO> selectCustomList();
	public List<CustomVO> selectImage(CustomVO vo);
	public CustomVO selectImageUrl(CustomVO vo);
	public int updateCustom(CustomVO vo);
	public int deleteCustom(CustomVO vo);
	
}
