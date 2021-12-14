package com.spring.service;

import java.util.List;

import com.spring.dto.ProductVO;

public interface ProductsService {
	public String selectImage();
	public List<ProductVO> selectList();
	public List<ProductVO> selectListColorAndSize(ProductVO vo);
	public Long selectCheckInsert(ProductVO vo);
	public boolean updateProduct(ProductVO vo);
	public boolean insertProduct(ProductVO vo);
	public boolean insertProductData(ProductVO vo);
	public List<ProductVO> selectProduct(ProductVO vo);
}
