package com.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring.dao.CustomDAO;
import com.spring.dto.CustomVO;

@Service
public class CustomServiceImpl implements CustomService {

	@Inject
	private CustomDAO dao;
	@Override
	public boolean insertCustom(CustomVO vo) {
		return 1==dao.insertCustom(vo);
	}
	@Override
	public List<CustomVO> selecCustomList() {
		// TODO Auto-generated method stub
		return dao.selectCustomList();
	}
	@Override
	public boolean updateCustom(CustomVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.updateCustom(vo);
	}
	@Override
	public boolean deleteCustom(CustomVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.deleteCustom(vo);
	}

}
