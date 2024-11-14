package com.pjw.retry_view.filter;

import com.pjw.retry_view.util.FilterUtil;
import com.pjw.retry_view.util.JWTUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTVerifyFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String method = request.getMethod();
        String jwt = request.getHeader("Authorization");

        System.out.println("Method: "+method+", uri: "+request.getRequestURI()+", JWT: "+jwt);
        if(isAllowMethod(method) && JWTUtil.isValidateToken(jwt)){
            String loginId = JWTUtil.getClaims(jwt).get("loginId").toString();
            request.setAttribute("loginId", loginId);
            filterChain.doFilter(request,response);
        }else{
            // accessToken 재발급받게 하기
            HttpServletResponse httpServletResponse = response;
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "권한이 없습니다.");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        return FilterUtil.isExcludeUrlPatterns(uri);
    }

    private boolean isAllowMethod(String method){
        return switch (method) {
            case "GET", "POST", "PUT", "DELETE" -> true;
            default -> false;
        };
    }
}
