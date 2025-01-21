package com.pjw.retry_view.controller;

import com.pjw.retry_view.response.JWToken;
import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.dto.UserInfo;
import com.pjw.retry_view.request.LoginRequest;
import com.pjw.retry_view.response.LoginResponse;
import com.pjw.retry_view.service.UserService;
import com.pjw.retry_view.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 API 컨트롤러", description = "로그인 API")
@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "일반 로그인 API", description = "")
    @PostMapping
    public ResponseEntity<LoginResponse> userLogin(@RequestBody @Valid LoginRequest loginReq){
        UserDTO user = userService.userLogin(loginReq);

        UserInfo userInfo = new UserInfo(user.getId(), user.getName(), user.getLoginId(), user.getRole());

        LoginResponse response = new LoginResponse();
        String refreshToken = JWTUtil.createRefreshToken();
        JWToken token = JWToken.getJWT(JWTUtil.createAccessToken(userInfo), refreshToken);

        response.setToken(token);

        user.changeRefereshToken(refreshToken);
        userService.saveUser(user);

        return new ResponseEntity<LoginResponse>(response, HttpStatus.OK);
    }
}
