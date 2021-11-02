package com.spring.service;

import java.util.List;

import com.spring.dto.ProductVO;

public interface ProductsService {
	public String selectImage();
	public List<ProductVO> selectList();
	public Long selectCheckInsert(ProductVO vo);
	public boolean updateProduct(ProductVO vo);
	public boolean insertProduct(ProductVO vo);
	public ProductVO selectProduct(ProductVO vo);
}
