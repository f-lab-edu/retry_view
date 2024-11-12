package com.pjw.retry_view.controller;

import com.pjw.retry_view.enums.UserAuth;
import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.request.RegistUserRequest;
import com.pjw.retry_view.response.RegistUserResponse;
import com.pjw.retry_view.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUsers() {
        return "MainController : " + userService.getUserList().stream().map(UserDTO::toString).collect(Collectors.joining(", "));
    }

    @GetMapping("/info")
    public String getUserInfo(HttpServletRequest req) {
        String loginId = req.getAttribute("loginId").toString();
        return "Login User Info : " + userService.getUserInfo(loginId);
    }

    @PostMapping("/regist")
    public ResponseEntity<RegistUserResponse> registUser(@RequestBody @Valid RegistUserRequest userReq, BindingResult bindingResult) {
        RegistUserResponse response = new RegistUserResponse();
        HttpStatus httpStatus = HttpStatus.OK;

        if (bindingResult.hasErrors()) {
            response.setBindingErrors(bindingResult.getAllErrors());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            UserDTO user = userReq.toUserDTO();
            user.setRole(UserAuth.USER);
            UserDTO registUser = userService.saveUser(user);
            response.setName(registUser.getName());
            response.setLoginId(registUser.getLoginId());
            response.setNickname(registUser.getNickname());
        }

        return new ResponseEntity<RegistUserResponse>(response, httpStatus);
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdrawUser(HttpServletRequest req){
        String loginId = req.getAttribute("loginId").toString();
        userService.withdrawUser(loginId);
        return ResponseEntity.ok("Success");
    }
}
