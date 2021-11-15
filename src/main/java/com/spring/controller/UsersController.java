package com.spring.controller;

import java.util.Date;

import javax.inject.Inject;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dto.UsersVO;
import com.spring.function.FunctionSpring;
import com.spring.service.UsersServiceImpl;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	 @Inject
	    private UsersServiceImpl usersService;
	
	@RequestMapping(value="/user-login",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userLogin(@RequestParam("id") String id,
			@RequestParam("password") String password) {
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO(id,password);
	
		vo = usersService.selectLogin(vo);
		try {
			vo.getUserNumber();			
		}catch (Exception e) {
			jsonObject.put("result", "아이디나 비밀번호를 확인하세요");
			return jsonObject.toString();
		}
		System.out.println(FunctionSpring.makeJwtToken());
		
		jsonObject.put("result", "로그인 성공");
		return jsonObject.toString();
	}
	@RequestMapping(value="/user-join",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userJoin(@RequestParam("id") String id,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam(value = "adress",required=false) String adress,
			@RequestParam(value = "phone",required=false) String phone) {
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO(id,password);
		vo.setRegDate(new Date());
		boolean join =usersService.insertUser(vo);
		String result = (join==true)?"Join":"Fail";
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
}
