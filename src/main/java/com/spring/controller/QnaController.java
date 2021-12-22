package com.spring.controller;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dto.CartVO;
import com.spring.dto.QnaVO;
import com.spring.service.QnaServiceImpl;

@Controller
public class QnaController {
	private static final Logger logger = LoggerFactory.getLogger(QnaController.class);
	private static String URL_PATH="http://ec2-3-37-117-153.ap-northeast-2.compute.amazonaws.com:8080/shoppingmall/com/product-image/";	
	@Autowired
	private QnaServiceImpl qService;
	
		//	Qna SELECT
		@CrossOrigin(origins = "*", allowedHeaders = "*")
		@RequestMapping(value = "/qna",method = RequestMethod.GET,produces = "application/json; charset=utf8")
		@ResponseBody
		public String qnaList()
		{
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArarry = new JSONArray();
			QnaVO vo = new QnaVO();
			List<QnaVO> sql=qService.selectListQna(vo);
			
			return jsonObject.toString();
		}
}

