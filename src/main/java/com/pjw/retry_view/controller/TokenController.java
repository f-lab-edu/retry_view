package com.pjw.retry_view.controller;

import com.pjw.retry_view.response.JWToken;
import com.pjw.retry_view.service.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/token")
public class TokenController {
    private final JWTService jwtService;

    public TokenController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<JWToken> renweAccessToken(@RequestBody JWToken token){
        return new ResponseEntity<JWToken>(jwtService.renewAccessToken(token.getRefreshToken()), HttpStatus.OK);
    }
}
