package com.pjw.retry_view.config;

import com.pjw.retry_view.enums.UserAuth;
import com.pjw.retry_view.filter.JWTVerifyFilter;
import com.pjw.retry_view.handler.OAuth2SuccessHandler;
import com.pjw.retry_view.service.OAuthUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final OAuthUserService oAuthUserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    public SecurityConfig(OAuthUserService oAuthUserService, OAuth2SuccessHandler oAuth2SuccessHandler) {
        this.oAuthUserService = oAuthUserService;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
            .headers(headersConfigurer -> headersConfigurer.frameOptions(
                HeadersConfigurer.FrameOptionsConfig::disable
            ))
            .sessionManagement(sessionManagementConfigurer -> {
                sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            })
            .authorizeHttpRequests(authorizeRequest -> {
                authorizeRequest.requestMatchers("/login/**","/users/regist","/ws","/chat/msg","/swagger-ui/**","/v3/api-docs/**", "/favicon.ico", "/error").permitAll()
                .requestMatchers("/admin**").hasRole(UserAuth.ADMIN.getCode())
                .requestMatchers("/**").hasAnyAuthority(UserAuth.USER.getCode(), UserAuth.ADMIN.getCode())
                .anyRequest().authenticated();
            })
            .oauth2Login(oAuth2LoginConfigurer -> { // OAuth2 설정
                oAuth2LoginConfigurer
                .userInfoEndpoint(userInfoEndpointConfig -> {
                    userInfoEndpointConfig.userService(oAuthUserService);
                }).successHandler(oAuth2SuccessHandler);
            })
            .exceptionHandling(exceptionConfig -> {
                exceptionConfig.authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler());
            })
            .addFilterBefore(new JWTVerifyFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
