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
		// TODO Auto-generated method stub
		return dao.selectList();
	}
	@Override
	public Long selectCheckInsert(ProductVO vo) {
		// TODO Auto-generated method stub
		System.out.println("service"+vo.toString());
		ProductVO vod = dao.selectCheckInsert(vo);
		if(vod!=null)
			return vod.getProductNumber();
		return (long) 0;
	}
	@Override
	public boolean updateProduct(ProductVO vo) {
		// TODO Auto-generated method stub
		
		return 1==dao.updateProduct(vo);
	}
	@Override
	public boolean insertProduct(ProductVO vo) {
		// TODO Auto-generated method stub
		return 1==dao.insertProduct(vo);
	}
	@Override
	public ProductVO selectProduct(ProductVO vo) {
		// TODO Auto-generated method stub
		return dao.selectProduct(vo);
	}

}
