package com.spring.config.interceptor;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.function.FunctionSpring;

@Component
public class JwtAuthInterceptor2 implements HandlerInterceptor {

	@Inject 
    private FunctionSpring functionSpring;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("by 2 jwt++");
		String method = request.getMethod();
		if(method.equals("GET"))
		{
			return true;
		}
		try {
			String[] token = request.getHeader("authorization").split(" ");
			String userId =functionSpring.parseringJwtToken(token[1]).get("id", String.class);
			System.out.println("성공");
			request.setAttribute("userNumber", userId);
			return true;
		}
		catch (Exception e) {
			System.out.println(e);
			response.sendError(404, "권한없는 사용자입니다.!");
			return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
