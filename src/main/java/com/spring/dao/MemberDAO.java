package com.spring.dao;

import java.util.List;

import com.spring.dto.MemberVO;


 
public interface MemberDAO {
    
    public List<MemberVO> selectMember() throws Exception;
}



