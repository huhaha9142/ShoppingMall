package com.spring.service;

import java.util.List;

import com.spring.dto.MemberVO;
 
public interface MemberService {
    
    public List<MemberVO> selectMember() throws Exception;
}






