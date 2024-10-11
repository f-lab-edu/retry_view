package com.pjw.retry_view.config;

import com.pjw.retry_view.converter.UserStateEnumConverter;
import com.pjw.retry_view.filter.JWTVerifyFilter;
import com.pjw.retry_view.filter.UserAuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JWTVerifyFilter jwtverifyFilter;
    private final UserAuthorizationFilter userAuthorizationFilter;

    public WebConfig(JWTVerifyFilter jwtVerifyFilter, UserAuthorizationFilter userAuthorizationFilter){
        this.jwtverifyFilter = jwtVerifyFilter;
        this.userAuthorizationFilter = userAuthorizationFilter;
    }

    @Bean
    public FilterRegistrationBean<JWTVerifyFilter> jwtVerifyFilterBean(){
        FilterRegistrationBean<JWTVerifyFilter> jwtVerifyFilterBean = new FilterRegistrationBean<>();
        jwtVerifyFilterBean.setFilter(jwtverifyFilter);
        jwtVerifyFilterBean.setOrder(1);
        jwtVerifyFilterBean.setUrlPatterns(List.of("*"));
        return jwtVerifyFilterBean;
    }

    @Bean
    public FilterRegistrationBean<UserAuthorizationFilter> userAuthorizationFilterBean(){
        FilterRegistrationBean<UserAuthorizationFilter> userAuthorizationFilterBean = new FilterRegistrationBean<>();
        userAuthorizationFilterBean.setFilter(userAuthorizationFilter);
        userAuthorizationFilterBean.setOrder(2);
        userAuthorizationFilterBean.setUrlPatterns(List.of("*"));

        return userAuthorizationFilterBean;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new UserStateEnumConverter());
    }
}
