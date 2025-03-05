package com.spring.FoodMate.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.spring.FoodMate.common.SessionDTO;

public class CommonInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 로그인 사용자 정보 가져오기
        String userId = "비로그인 회원";
        if (request.getSession().getAttribute("sessionDTO") != null) {
            userId = ((SessionDTO) request.getSession().getAttribute("sessionDTO")).getUserId();
        }
        
        // 요청 정보
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String ipAddress = request.getRemoteAddr();
        
        // 요청 로깅
        logger.info(String.format("요청 - 사용자: %s, 메서드: %s, URI: %s, IP: %s", 
        	    userId != null ? userId : "Anonymous", 
        	    method, 
        	    requestURI, 
        	    ipAddress));
        
        return true; // 요청 처리 계속 진행
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView mav) throws Exception {
        // 응답 상태 코드 로깅
        int status = response.getStatus();
        logger.info("RESPONSE - Status: {}", status);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 예외 처리 (옵션)
        if (ex != null) {
            logger.error("Exception occurred: {}", ex.getMessage());
        }
    }
}