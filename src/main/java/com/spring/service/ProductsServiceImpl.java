package com.spring.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring.dao.ProductsDAO;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Inject
	private ProductsDAO dao;
	@Override
	public String selectImage() {
		// TODO Auto-generated method stub
		return dao.selectImageString();
	}

}
