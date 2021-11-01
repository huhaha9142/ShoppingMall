package com.spring.service;

import java.util.List;

import com.spring.dto.ProductVO;

public interface ProductsService {
	public String selectImage();
	public List<ProductVO> selectList();
	public boolean selectCheckInsert(ProductVO vo);
}
