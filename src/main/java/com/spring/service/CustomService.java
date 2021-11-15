package com.spring.service;

import java.util.List;

import com.spring.dto.CustomVO;

public interface CustomService {
	public boolean insertCustom(CustomVO vo);
	public List<CustomVO> selecCustomList();
	public boolean updateCustom(CustomVO vo);
	public boolean deleteCustom(CustomVO vo);
}
