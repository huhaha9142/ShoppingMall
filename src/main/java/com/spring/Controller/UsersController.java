package com.spring.Controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.dto.UsersVO;
import com.spring.service.UsersService;

@Controller
@RequestMapping
public class UsersController {

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	@Inject
	UsersService service;
	
	//	spring 시큐리티 (암호화 시키는 코드?)
	@Autowired
	BCryptPasswordEncoder passEncoder;
	
	//	회원가입 get
	@RequestMapping(value = "/userJoin", method = RequestMethod.GET)
	public void getUserJoin() throws Exception {
		logger.info("get signup");
	}
	
	//	회원가입 post
	@RequestMapping(value = "/userJoin", method = RequestMethod.POST)
	public String postUserJoin(UsersVO vo) throws Exception {
		logger.info("post signup");
		
		String inputPass = vo.getPassword();
		String pass = passEncoder.encode(inputPass);
		vo.setPassword(pass);
		
		service.userJoin(vo);
		
		return "redirect:/";
	}
	
	// 로그인  get
	@RequestMapping(value = "/userLogin", method = RequestMethod.GET)
	public void getUserLogin() throws Exception {
	 logger.info("get UserLogin");
	}

	// 로그인 post
	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public String postSignin(UsersVO vo, HttpServletRequest req, RedirectAttributes rttr) throws Exception {
	 logger.info("post signin");
	   
	 UsersVO login = service.userLogin(vo);  
	 HttpSession session = req.getSession();
	 
	 boolean passMatch = passEncoder.matches(vo.getPassword(), login.getPassword());
	 
	 // passMatch : 사용자가 입력한 pw와 db에 저장된 pw를 비교해서 동일하면 login, 아니면 false
	 if(login != null && passMatch) {
	  session.setAttribute("user", login);	//	id, pw가 모두 맞게 입력되어야 실행
	 } else {
	  session.setAttribute("user", null);	//	id, pw가 모두 맞게 입력되어야 실행
	  rttr.addFlashAttribute("usg", false);
	  return "redirect:/user/userLogin";
	 }  
	 
	 /*아이디나 패스워드 중 하나라도 틀리다면 else { } 부의 코드가 실행되는데 
	 session.setAttribute("member", null); 로 세션값을 제거하고 
	 rttr.addFlashAttribute("msg", false); 는 특정 페이지로 이동될 때 msg의 값(false)를 부여합니다. 
	 이때 특정 페이지는 return "redirect:/member/signin"; 가 됩니다. */
	 
	 return "redirect:/";
	}
	  
	// 로그아웃
	@RequestMapping(value = "/userLogout", method = RequestMethod.GET)
	public String userLogout(HttpSession session) throws Exception {
	 logger.info("get userLogout");
	 
	 service.userLogout(session);
	   
	 return "redirect:/";
	}
}
