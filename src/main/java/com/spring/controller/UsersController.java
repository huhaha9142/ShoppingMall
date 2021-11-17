package com.spring.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class UsersController {
	private static final Logger logger = LoggerFactory.getLogger(ProductsController.class);
	 @Inject
	    private UsersServiceImpl usersService;
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
		Cookie cookie = new Cookie("token", FunctionSpring.makeJwtToken(id,password));
		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000"); //허용대상 도메인
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT"); 
		response.addHeader("Access-Control-Max-Age", "3600"); 
		response.addHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept, Authorization"); 
		response.setHeader("Authorization", FunctionSpring.makeJwtToken(id,password));
//		cookie.setSecure(true);
//		cookie.setPath("/"); // global cookie accessible every where
		response.addCookie(cookie);
		jsonObject.put("result", "Success");
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user-login/jwt",method = RequestMethod.GET,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userJwtTokenCheck(@CookieValue(value = "token", defaultValue = "Atta") String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException
	{
		JSONObject jsonObject = new JSONObject();
		System.out.println(token);
		String userId =FunctionSpring.parseringJwtToken(token).get("id", String.class);
		jsonObject.put("id",userId);
		return jsonObject.toString();
	}
	
	@RequestMapping(value="/user-join",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userJoin(@RequestParam("id") String id,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam(value = "address",required=false) String address,
			@RequestParam(value = "phone",required=false) String phone) {
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO(id,password);
		vo.setName(name);
		vo.setRegDate(new Date());
		vo.setInDate(new Date());
		boolean join = usersService.insertUser(vo);
		String result = (join==true)?"Join":"Fail";
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	// id duplicateCheck API
	@RequestMapping(value="/user-id",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String idDuplicateCheck(@RequestParam("id") String id) {
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setId(id);	
		String result = (usersService.selectIdCheck(vo)==1)?"이미 사용중입니다":"사용 가능한 아이디입니다."; 		
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	// user privacy update API
	@RequestMapping(value="/user-privacy",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userPrivacy(@RequestParam("name") String name,
			@RequestParam("nickName") String nickName,
			@RequestParam(value = "address",required=false) String address,
			@RequestParam(value = "phone",required=false) String phone,
			@RequestParam("userNumber") String userNumber) {
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setAddress(address);
		vo.setName(name);
		vo.setNickName(nickName);
		vo.setPhone(phone);
		vo.setRegDate(new Date());
		vo.setUserNumber(Long.valueOf(userNumber));
		
		String result = (usersService.updateUserPrivacy(vo)==true)?"update":"fail";
		
		jsonObject.put("result", result);
	
		return jsonObject.toString();
	}
	// user password update API
	@RequestMapping(value="/user-password",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userPassword(@RequestParam("password") String password,
			@RequestParam("userNumber") String userNumber) {
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setPassword(password);
		vo.setUserNumber(Long.valueOf(userNumber));
		String result = (usersService.updatePassword(vo)==true)?"update":"fail";
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	// user delete API
	@RequestMapping(value="/user",method = RequestMethod.DELETE,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userDelete(@RequestParam("password") String password,
			@RequestParam("userNumber") String userNumber) {
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setUserNumber(Long.valueOf(userNumber));
		String result = (usersService.updatePassword(vo)==true)?"update":"fail";
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	
}
