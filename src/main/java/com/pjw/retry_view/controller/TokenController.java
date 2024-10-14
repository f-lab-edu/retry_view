package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.RefreshTokenDTO;
import com.pjw.retry_view.response.JWToken;
import com.pjw.retry_view.service.JWTService;
import com.pjw.retry_view.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;

@Controller
@RequestMapping("/token")
public class TokenController {
    private final JWTService jwtService;
    @Autowired
    private RedisService redisService;

    public TokenController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping
    public JWToken renewAccessToken(@RequestBody JWToken token){
        return jwtService.renewAccessToken(token.getRefreshToken());
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(){
        redisService.setValues(RefreshTokenDTO.getRefreshToken("qkrwldnjs","tokenTEST", Duration.ofDays(7L)));
        return new ResponseEntity<>(redisService.getValue("qkrwldnjs").getRefreshToken(), HttpStatus.OK);
    }
}
