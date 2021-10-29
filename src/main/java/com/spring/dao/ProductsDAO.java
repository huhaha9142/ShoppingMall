package com.spring.dao;

import java.util.List;

import com.spring.dto.ProductVO;

public interface ProductsDAO {
	public String selectImageString();
	public List<ProductVO> selectList();
}
