package com.spring.service;

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

}
