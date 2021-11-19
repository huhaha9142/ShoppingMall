package com.spring.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.dto.ProductVO;

@Repository
public class ProductsDAOImpl implements ProductsDAO {

	@Inject
    private SqlSession sqlSession;
	
	private static final String Namespace = "com.example.mapper.products";
	
	@Override
	public String selectImageString() {
		return sqlSession.selectOne(Namespace+".selectImage");
	}

	@Override
	public List<ProductVO> selectList() {		
		return sqlSession.selectList(Namespace+".selectList");
	}

	@Override
	public ProductVO selectCheckInsert(ProductVO vo) {
		System.out.println("DAO"+vo.toString());
		return sqlSession.selectOne(Namespace+".selectCheckInsert", vo);
	}

	@Override
	public int updateProduct(ProductVO vo) {
		
		return sqlSession.update(Namespace+".updateProduct", vo);
	}

	@Override
	public int insertProduct(ProductVO vo) {
		return sqlSession.insert(Namespace+".insertProduct", vo);
				
	}

	@Override
	public List<ProductVO> selectProduct(ProductVO vo) {
		return sqlSession.selectList(Namespace+".selectProduct", vo);
	}

	@Override
	public int insertProductData(ProductVO vo) {
		return sqlSession.insert(Namespace+".insertProductData", vo);
	}

	@Override
	public List<ProductVO> selectListColorAndSize(ProductVO vo) {
		return sqlSession.selectList(Namespace+".selectListColorAndSize", vo);
	}

}
