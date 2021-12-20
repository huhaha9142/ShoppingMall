package com.spring.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.spring.dto.UsersVO;
import com.spring.function.FunctionSpring;
import com.spring.service.UsersServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Controller
@CrossOrigin(allowCredentials = "false")
public class UsersController {
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	@Inject
	    private UsersServiceImpl usersService;
	@Inject
		private JavaMailSender mailSender;
	@Inject 
	    private FunctionSpring functionSpring;
	// 변경시 카카오 디벨로퍼에서도 변경점 등록해줘야 함.!
	private static String EMAIL_URL = "/";
	private static String REDIRECT_URL = "http://customshoppingmall.kro.kr/loading";
	@CrossOrigin(origins = "*", exposedHeaders = "Authorization", allowedHeaders = "*")
	@RequestMapping(value="/user-login",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userLogin(@RequestParam("id") String id,
			@RequestParam("password") String password,
			HttpServletResponse response) {
		
		JSONObject jsonObject = new JSONObject();
		BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
		UsersVO vo = new UsersVO(id,password);
//		response.addHeader("Access-Control-Allow-Origin", "http://pvpvpvpvp.gonetis.com:3000");		
//		response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");	
//		response.addHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT"); 
//		response.addHeader("Access-Control-Max-Age", "1890"); 
//		response.addHeader("Access-Control-Allow-Credentials", "true");
//		response.addHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept, Authorization"); 
		UsersVO vo1 = usersService.selectLogin(vo);
		
		try {
			if(!scpwd.matches(vo.getPassword(),vo1.getPassword()))
			{
				System.err.println(scpwd.matches(vo.getPassword(),vo1.getPassword()));
				System.err.println(vo.getPassword());
				jsonObject.put("result", "Fail");
				return jsonObject.toString();
			}
			vo1.getUserNumber();
			if(vo1.getRule().contains("uncertified"))
			{
				jsonObject.put("result", "Uncertified");
				return jsonObject.toString();
			}
		}catch (Exception e) {
			System.err.println(e);
			jsonObject.put("result", "Fail");
			return jsonObject.toString();
		}
		
//		Cookie cookie = new Cookie("tokenCookie", functionSpring.makeJwtToken(id,password));
//		cookie.setMaxAge(60*30);
//		cookie.setPath("/");
//		cookie.setSecure(true);
//	    cookie.setHttpOnly(true);	 
//		response.addCookie(cookie);
		
		response.setHeader("Authorization","jwt "+functionSpring.makeJwtToken(String.valueOf(vo1.getUserNumber())));
		jsonObject.put("nickName",vo1.getName());
		jsonObject.put("result", "Success");
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user",method = RequestMethod.GET,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userJwtTokenCheck(HttpServletRequest httpServletRequest)
	{
		Long userNumber  = Long.valueOf((String)httpServletRequest.getAttribute("userNumber"));
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setUserNumber(userNumber);
		UsersVO sql = usersService.selectUserPrivacy(vo);
		jsonObject.put("nickName", sql.getNickName());
		return jsonObject.toString();
		
	}
	@CrossOrigin(origins = "*", exposedHeaders = "Authorization", allowedHeaders = "*")
	@RequestMapping(value="/user-kakao",method = RequestMethod.GET,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userLoginWithKakao(@RequestParam("code") String code,
			HttpServletResponse response) {
		System.out.println(code);
		JSONObject jsonObject = new JSONObject();
		JSONParser jsonParser = new JSONParser();
		String accessToken = null;
		String kakaoIdNumber = null;
		Long userNumber;
		UsersVO vo = new UsersVO();
		UsersVO sql = new UsersVO();
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		// 전달받은 코드를 카카오 서버에 전달해서 엑세스 토큰 발급받기!
		try {			
			String url = "https://kauth.kakao.com/oauth/token";
			MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
			map.add("code", code);
			map.add("grant_type", "authorization_code");
			map.add("client_id", "8c2835e5881d60b38a8561176852e4e2");
			//TODO: 리다이렉트   URL
			map.add("redirect_uri", REDIRECT_URL);
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(map,headers);
			String answer = restTemplate.postForObject(url, entity, String.class);
			System.out.println(answer);	
			JSONObject token = (JSONObject) jsonParser.parse(answer);
			accessToken = token.get("token_type")+" "+token.get("access_token");
		}catch (Exception e) {
			System.err.println(e);
		}
		// 전달 받은 엑세스 토큰을 이용해 카카오 id(회원번호) 받아오기!
		try {	
			String url = "https://kapi.kakao.com/v2/user/me";
			headers.add("Authorization", accessToken);
			MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity(map,headers);
			String answer = restTemplate.postForObject(url, entity, String.class);
			System.out.println(answer);	
			JSONObject token = (JSONObject) jsonParser.parse(answer);	
			kakaoIdNumber = token.get("id")+"";
			System.out.println(kakaoIdNumber);
			vo.setKakao(kakaoIdNumber);
			System.out.println(vo.getKakao());
		}catch (Exception e) {
			System.err.println(e);
		}
		try {
			sql = usersService.selectLoginKakao(vo);
			userNumber = sql.getUserNumber();
		} catch (Exception e) {
			System.out.println("회원가입 필요");
			jsonObject.put("result", "Redirect Social Join");
			jsonObject.put("kakaoNumber", kakaoIdNumber);
			return jsonObject.toString();
		}
		
		System.out.println(userNumber);
		response.setHeader("Authorization","jwt "+functionSpring.makeJwtToken(String.valueOf(userNumber)));
		jsonObject.put("result", "Success");
		jsonObject.put("nickName",sql.getName());
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user-join",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userJoin(@RequestParam("id") String id,
			@RequestParam("password") String password,
			@RequestParam("name") String name,
			@RequestParam(value = "address",required=false) String address,
			@RequestParam(value = "phone",required=false) String phone) {
		JSONObject jsonObject = new JSONObject();
		BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
		UsersVO vo = new UsersVO(id,scpwd.encode(password));
		vo.setName(name);
		vo.setRegDate(new Date());
		vo.setInDate(new Date());
		String key= "uncertified"+UUID.randomUUID();
		vo.setRule(key);
		boolean join = usersService.insertUser(vo);
		String result = (join==true)?"Join":"Fail";
		if(join)
		{
			MimeMessage mail = mailSender.createMimeMessage();
			String htmlStr = "<h2>안녕하세요 MS :p CUSTOM SHOPPINGMALL 입니다!</h2><br><br>" 
					+ "<h3>" + id + "님</h3>" + "<p>인증하기 버튼을 누르시면 로그인을 하실 수 있습니다 : " 
					+ "<a href='http://customshoppingmall.kro.kr/"+"/email-cerified?id="+ id +"&key="+key+"'>인증하기</a></p>"
					+ "(혹시 잘못 전달된 메일이라면 이 이메일을 무시하셔도 됩니다)";
			try {
				mail.setSubject("[본인인증] MS :p CUSTOM SHOPPINGMALL의 인증메일입니다", "utf-8");
				mail.setText(htmlStr, "utf-8", "html");
				mail.addRecipient(RecipientType.TO, new InternetAddress(id));
				mailSender.send(mail);
			} catch (MessagingException e) {
				e.printStackTrace();
			}

		}
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", exposedHeaders = "Authorization",allowedHeaders = "*")
	@RequestMapping(value="/user-join-social",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userSocialJoin(
			@RequestParam("name") String name,
			@RequestParam("kakaoNumber") String kakaoNumber,
			HttpServletResponse response) {
		UsersVO vo = new UsersVO();
		vo.setName(name);
		vo.setKakao(kakaoNumber);
		vo.setRegDate(new Date());
		vo.setInDate(new Date());
		vo.setRule("socialUser");
		String result = (true==usersService.insertUserSocial(vo))?"Success":"Fail";
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		if(result.equals("Success"))
		{
			Long userNumber = usersService.selectLoginKakao(vo).getUserNumber();
			response.setHeader("Authorization","jwt "+functionSpring.makeJwtToken(String.valueOf(userNumber)));
		}
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user-join-email",method = RequestMethod.GET,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userJoinEmailCheck(@RequestParam(value = "id") String id,
			@RequestParam(value = "key") String key)
	{
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setRule(key);
		vo.setId(id);
		String result = (true==usersService.updateRuleByEmail(vo))?"success":"fail";
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	
	//TODO:: URL CHECK
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user/reset",method = RequestMethod.GET,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userPasswordReseting(@RequestParam(value = "id") String id)
	{
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setId(id);
		UsersVO sql = usersService.selectUserRule(vo);
		String key = null;
		if(!sql.getRule().contains("uncertified"))
		{
			key= "user"+UUID.randomUUID();
			vo.setRule(key);
		}
		boolean update = usersService.updateRulePassword(vo);
		String result = (true==update)?"success":"fail";
		if(update)
		{
			MimeMessage mail = mailSender.createMimeMessage();
			String htmlStr = "<h2>안녕하세요 MS :p CUSTOM SHOPPINGMALL 입니다!</h2><br><br>" 
					+ "<h3>" + id + "님</h3>" + "<p>비밀번호 재설정 버튼을 누르시면 비밀번호를 재설정 하실 수 있습니다.: " 
					+ "<a href='http://customshoppingmall.kro.kr/passwordreset"+"?id="+ id +"&key="+key+"'>인증하기</a></p>"
					+ "(혹시 잘못 전달된 메일이라면 이 이메일을 무시하셔도 됩니다)";
			try {
				mail.setSubject("[본인인증] MS :p CUSTOM SHOPPINGMALL의 비밀번호 재설정 메일입니다", "utf-8");
				mail.setText(htmlStr, "utf-8", "html");
				mail.addRecipient(RecipientType.TO, new InternetAddress(id));
				mailSender.send(mail);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}	
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user/reset",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userPasswordReset(@RequestParam(value = "id") String id,
			@RequestParam(value = "key") String key,
			@RequestParam(value = "password") String password)
	{
		JSONObject jsonObject = new JSONObject();
		BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
		UsersVO vo = new UsersVO(id,scpwd.encode(password));
		vo.setRule(key);
		String result = (true==usersService.updatePasswordByEmail(vo))?"success":"fail";
		jsonObject.put("result", result); 
		return jsonObject.toString();
	}
	
	// id duplicateCheck API
	@CrossOrigin(origins = "*", allowedHeaders = "*")
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
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user-privacy",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userPrivacy(@RequestParam("name") String name,
			@RequestParam(value = "nickName",required=false) String nickName,
			@RequestParam(value = "address1",required=false) String address1,
			@RequestParam(value = "address2",required=false) String address2,
			@RequestParam(value = "address3",required=false) String address3,
			@RequestParam(value = "address4",required=false) String address4,
			@RequestParam(value = "phone",required=false) String phone,
			HttpServletRequest httpServletRequest) {
		Long userNumber  = Long.valueOf((String)httpServletRequest.getAttribute("userNumber"));
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setAddress1(address1);
		vo.setAddress2(address2);
		vo.setAddress3(address3);
		vo.setAddress4(address4);
		vo.setName(name);
		vo.setNickName(nickName);
		vo.setPhone(phone);
		vo.setRegDate(new Date());
		vo.setUserNumber(userNumber);
		
		String result = (usersService.updateUserPrivacy(vo)==true)?"update":"fail";
		
		jsonObject.put("result", result);
	
		return jsonObject.toString();
	}
	// user password update API
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user-password",method = RequestMethod.POST,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userPassword(@RequestParam("password") String password,
			HttpServletRequest httpServletRequest) {
		Long userNumber  = Long.valueOf((String)httpServletRequest.getAttribute("userNumber"));
		JSONObject jsonObject = new JSONObject();
		BCryptPasswordEncoder scpwd = new BCryptPasswordEncoder();
		UsersVO vo = new UsersVO();
		vo.setPassword(scpwd.encode(password));
		vo.setUserNumber(userNumber);
		String result = (usersService.updatePassword(vo)==true)?"update":"fail";
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	// user delete API
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user",method = RequestMethod.DELETE,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userDelete(@RequestParam("password") String password,
			HttpServletRequest httpServletRequest) {
		String userNumber = (String) httpServletRequest.getAttribute("userNumber");
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setUserNumber(Long.valueOf(userNumber));
		//TODO:: 스케줄러로 지연삭제 시키기 룰 바꾸기 적용하기
		String result = (usersService.updatePassword(vo)==true)?"update":"fail";
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value="/user-privacy",method = RequestMethod.GET,produces = "application/json; charset=utf8")
	@ResponseBody
	public String userPrivacy(HttpServletRequest httpServletRequest) {
		System.out.println("유저 정보 불러기가 작동");
		Long userNumber  = Long.valueOf((String)httpServletRequest.getAttribute("userNumber"));
		JSONObject jsonObject = new JSONObject();
		UsersVO vo = new UsersVO();
		vo.setUserNumber(userNumber);
		UsersVO sql = usersService.selectUserPrivacy(vo);
		jsonObject.put("nickName", sql.getNickName());
		
		jsonObject.put("id", sql.getId());
		jsonObject.put("address1", sql.getAddress1());
		jsonObject.put("address2", sql.getAddress2());
		jsonObject.put("address3", sql.getAddress3());
		jsonObject.put("address4", sql.getAddress4());
		jsonObject.put("phone", sql.getPhone());
		return jsonObject.toString();
	}
	
}
