package com.spring.dao;

import java.util.List;

import com.spring.dto.ProductVO;

public interface ProductsDAO {
	public String selectImageString();
	public List<ProductVO> selectList();
	public ProductVO selectCheckInsert(ProductVO vo);
	public int updateProduct(ProductVO vo);
	public int insertProduct(ProductVO vo);
	public ProductVO selectProduct(ProductVO vo);
}
