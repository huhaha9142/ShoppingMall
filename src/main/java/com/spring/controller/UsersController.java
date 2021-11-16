package com.spring.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.dto.UsersVO;
import com.spring.function.FunctionSpring;
import com.spring.service.UsersServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	 @Inject
	    private UsersServiceImpl usersService;
	@CrossOrigin(origins = "*", allowedHeaders = "*",exposedHeaders = "Authorization")
	@RequestMapping(value="/user-login",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userLogin(@RequestParam("id") String id,
			@RequestParam("password") String password,
			HttpServletResponse response) {
		
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO(id,password);
	
		vo = usersService.selectLogin(vo);
		try {
			vo.getUserNumber();			
		}catch (Exception e) {
			jsonObject.put("result", "Fail");
			return jsonObject.toString();
		}
		response.setHeader("Authorization", FunctionSpring.makeJwtToken(id,password));
		jsonObject.put("result", "Success");
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user-login/jwt",method = RequestMethod.GET,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userJwtTokenCheck(@RequestHeader String Authorization) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException
	{
		JSONObject jsonObject = new JSONObject();
		String userId =FunctionSpring.parseringJwtToken(Authorization).get("id", String.class);
		jsonObject.put("id",userId);
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
		boolean join = usersService.insertUser(vo);
		String result = (join==true)?"Join":"Fail";
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
}
