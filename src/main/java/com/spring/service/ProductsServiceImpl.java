package com.spring.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.spring.dao.ProductsDAO;
import com.spring.dto.ProductVO;

@Service
public class ProductsServiceImpl implements ProductsService {

	@Inject
	private ProductsDAO dao;
	@Override
	public String selectImage() {
		return dao.selectImageString();
	}
	@Override
	public List<ProductVO> selectList() {
		return dao.selectList();
	}
	@Override
	public Long selectCheckInsert(ProductVO vo) {
		System.out.println("service"+vo.toString());
		ProductVO vod = dao.selectCheckInsert(vo);
		if(vod!=null)
			return vod.getProductNumber();
		return (long) 0;
	}
	@Override
	public boolean updateProduct(ProductVO vo) {		
		return 1==dao.updateProduct(vo);
	}
	@Override
	public boolean insertProduct(ProductVO vo) {
		return 1==dao.insertProduct(vo);
	}
	@Override
	public List<ProductVO> selectProduct(ProductVO vo) {
		return dao.selectProduct(vo);
	}
	@Override
	public boolean insertProductData(ProductVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.insertProductData(vo);
	}
	@Override
	public List<ProductVO> selectListColorAndSize(ProductVO vo) {
		// TODO Auto-generated method stub
		return dao.selectListColorAndSize(vo);
	}

}
