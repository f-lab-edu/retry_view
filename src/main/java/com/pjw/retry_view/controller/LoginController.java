package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.dto.UserInfo;
import com.pjw.retry_view.request.LoginRequest;
import com.pjw.retry_view.response.LoginResponse;
import com.pjw.retry_view.service.JWTService;
import com.pjw.retry_view.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;
    private final JWTService jwtService;

    public LoginController(UserService userService, JWTService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> userLogin(@RequestBody @Valid LoginRequest loginReq){
        UserDTO user = userService.userLogin(loginReq);

        UserInfo userInfo = new UserInfo();
        userInfo.setName(user.getName());
        userInfo.setLoginId(user.getLoginId());

        LoginResponse response = new LoginResponse();
        response.setAccessToken(jwtService.createAccessToken(userInfo));
        response.setRefreshToken(jwtService.createRefreshToken());
        return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
    }
}
