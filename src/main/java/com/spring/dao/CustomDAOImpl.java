package com.spring.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.spring.dto.CustomVO;

@Repository
public class CustomDAOImpl implements CustomDAO {

	@Inject
	private SqlSession sqlSession;
	private static final String Namespace = "com.example.mapper.customMapper";
	@Override
	public int insertCustom(CustomVO vo) {
		return sqlSession.insert(Namespace+".insertCustom", vo);
	}
	@Override
	public List<CustomVO> selectCustomList() {
		return sqlSession.selectList(Namespace+".selectCustomList");
	}
	@Override
	public int updateCustom(CustomVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.update(Namespace+".updateCustom", vo);
	}
	@Override
	public int deleteCustom(CustomVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.delete(Namespace+".deleteCustom", vo);
	}
	@Override
	public List<CustomVO> selectImage(CustomVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(Namespace+".selectImage", vo);
	}
	@Override
	public CustomVO selectImageUrl(CustomVO vo) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(Namespace+".selectImageUrl", vo);
	}

}
