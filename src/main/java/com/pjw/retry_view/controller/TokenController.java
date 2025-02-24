package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.RefreshToken;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.response.JWToken;
import com.pjw.retry_view.service.JWTService;
import com.pjw.retry_view.service.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;

@Tag(name = "JWT관리 API 컨트롤러", description = "")
@Controller
@RequestMapping("/token")
public class TokenController {
    private final JWTService jwtService;
    private final RedisService redisService;

    public TokenController(JWTService jwtService, RedisService redisService) {
        this.jwtService = jwtService;
        this.redisService = redisService;
    }

    @Operation(summary = "refresh 토큰 갱신 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.USER_NOT_FOUND, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @PostMapping
    public JWToken renewAccessToken(@RequestBody JWToken token){
        return jwtService.renewAccessToken(token.getRefreshToken());
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        String key = "key";
        String value = "qkrwldnjs";
        Duration duration = Duration.ofDays(7L);
        RefreshToken token = RefreshToken.getRefreshToken(key, value, Duration.ofDays(1));
        redisService.setValues(token.getKey(), value, duration);
        return new ResponseEntity<>(token.getRefreshToken(), HttpStatus.OK);
    }
}
