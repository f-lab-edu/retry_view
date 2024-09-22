package com.pjw.retry_view.filter;

import com.pjw.retry_view.service.JWTService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTVerifyFilter implements Filter {
    private final JWTService jwtService;
    public JWTVerifyFilter(JWTService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        String method = httpServletRequest.getMethod();
        String jwt = httpServletRequest.getHeader("Authorization");

        System.out.println("Method: "+method+", JWT: "+jwt);
        if(isAllowMethod(method) && jwtService.validateToken(jwt)){
            String loginId = jwtService.getClaims(jwt).get("loginId").toString();
            servletRequest.setAttribute("loginId", loginId);
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            // accessToken 재발급받게 하기
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "권한이 없습니다.");
        }
    }

    private boolean isAllowMethod(String method){
        return switch (method) {
            case "GET", "POST", "PUT", "DELETE" -> true;
            default -> false;
        };
    }
}
