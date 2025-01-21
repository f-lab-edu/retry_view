package com.pjw.retry_view.aop;


import com.pjw.retry_view.exception.DuplicateRequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class DuplicateRequestAop {
    private final RedisTemplate<String, String> redisTemplate;

    public DuplicateRequestAop(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("execution(* com.pjw.retry_view.controller.*Controller.*(..))")
    public Object duplicateRequestCallCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String httpMethod = request.getMethod();
        String reqKey = "req:"+request.getHeader("reqKey");

        if ("GET".equalsIgnoreCase(httpMethod)) {
            return joinPoint.proceed();
        }

        boolean result = redisTemplate.opsForValue().setIfAbsent(reqKey, "api call", 10, TimeUnit.SECONDS);
        if(result) {
            return joinPoint.proceed();
        }else{
            throw new DuplicateRequestException("중복된 요청입니다.");
        }
    }
}
