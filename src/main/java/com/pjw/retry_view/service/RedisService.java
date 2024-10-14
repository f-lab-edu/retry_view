package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.RefreshTokenDTO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void setValues(RefreshTokenDTO refreshTokenDTO){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(refreshTokenDTO.getKey(), refreshTokenDTO.getRefreshToken());
    }

    public RefreshTokenDTO getValue(String key){
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        Object value = values.get(key);
        if(value == null) return null;
        return RefreshTokenDTO.getRefreshToken("key",String.valueOf(value), Duration.ofDays(1));
    }

    public void deleteValue(String key){
        redisTemplate.delete(key);
    }
}
