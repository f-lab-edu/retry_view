package com.pjw.retry_view.filter;

import com.pjw.retry_view.enums.UserAuth;
import com.pjw.retry_view.util.FilterUtil;
import com.pjw.retry_view.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String jwt = request.getHeader(JWTUtil.AUTH_KEY);
        UserAuth userAuth = JWTUtil.getUserAuthInJWT(jwt);
        if(uri.startsWith(FilterUtil.ADMIN_URL) && !UserAuth.ADMIN.equals(userAuth)){
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "권한이 없습니다.");
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        return FilterUtil.excludeUrlPatterns.contains(uri);
    }
}
