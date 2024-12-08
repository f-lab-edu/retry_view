package com.pjw.retry_view.handler;

import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.dto.UserInfo;
import com.pjw.retry_view.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private static final String SUCCESS_URI = "http://fornt-url";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetail user = (UserDetail)authentication.getPrincipal();

        UserInfo userInfo = new UserInfo(user.getId(), user.getName(), user.getUsername(), user.getRole());
        String accessToken = JWTUtil.createAccessToken(userInfo);
        String refreshToken = JWTUtil.createRefreshToken();

        // 토큰 전달을 위한 redirect
        String redirectUrl = UriComponentsBuilder.fromUriString(SUCCESS_URI)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().toUriString();

        response.sendRedirect(redirectUrl);
    }

}
