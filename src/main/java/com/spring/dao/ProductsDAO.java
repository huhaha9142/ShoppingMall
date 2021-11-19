package com.spring.dao;

import java.util.List;

import com.spring.dto.ProductVO;

public interface ProductsDAO {
	public String selectImageString();
	public List<ProductVO> selectList();
	public List<ProductVO> selectListColorAndSize(ProductVO vo);
	public ProductVO selectCheckInsert(ProductVO vo);
	public int updateProduct(ProductVO vo);
	public int insertProduct(ProductVO vo);
	public int insertProductData(ProductVO vo);
	public List<ProductVO> selectProduct(ProductVO vo);
	
}
