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
	public boolean selectCheckInsert(ProductVO vo) {
		// TODO Auto-generated method stub
		System.out.println("service"+vo.toString());
		ProductVO vod = dao.selectCheckInsert(vo);
		int i=0;
		try
		{
			i=vod.toString().length();
		}catch (Exception e) {
			// TODO: handle exception
			
		}
		return 1==i;
	}

}
