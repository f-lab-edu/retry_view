package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.RefreshTokenDTO;
import io.micrometer.common.util.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public void setValues(String key, String value, Duration duration){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, value, duration);
    }

    public String getValue(String key){
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String value = values.get(key);
        if(StringUtils.isBlank(value)) return null;
        return value;
    }

    public void deleteValue(String key){
        redisTemplate.delete(key);
    }
}
