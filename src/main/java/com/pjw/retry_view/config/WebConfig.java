package com.pjw.retry_view.config;

import com.pjw.retry_view.converter.UserStateEnumConverter;
import com.pjw.retry_view.filter.JWTVerifyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final JWTVerifyFilter jwtverifyFilter;

    public WebConfig(JWTVerifyFilter jwtVerifyFilter){
        this.jwtverifyFilter = jwtVerifyFilter;
    }

    @Bean
    public FilterRegistrationBean<JWTVerifyFilter> jwtVerifyFilterBean(){
        FilterRegistrationBean<JWTVerifyFilter> jwtVerifyFilterBean = new FilterRegistrationBean<>();
        jwtVerifyFilterBean.setFilter(jwtverifyFilter);
        jwtVerifyFilterBean.setOrder(1);
        jwtVerifyFilterBean.setUrlPatterns(List.of("/users","/users/info","/logout","/users/withdraw"));
        return jwtVerifyFilterBean;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        WebMvcConfigurer.super.addFormatters(registry);
        registry.addConverter(new UserStateEnumConverter());
    }
}
