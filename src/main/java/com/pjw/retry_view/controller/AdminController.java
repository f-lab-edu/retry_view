package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.UserAuth;
import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.request.RegistUserRequest;
import com.pjw.retry_view.response.RegistUserResponse;
import com.pjw.retry_view.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/regist")
    public ResponseEntity<RegistUserResponse> registAdminUser(@RequestBody @Valid RegistUserRequest userReq, BindingResult bindingResult) {
        RegistUserResponse response = new RegistUserResponse();
        HttpStatus httpStatus = HttpStatus.OK;

        if (bindingResult.hasErrors()) {
            response.setBindingErrors(bindingResult.getAllErrors());
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            UserDTO user = userReq.toUserDTO();
            user.setRole(UserAuth.ADMIN);
            UserDTO registUser = userService.saveUser(user);
            response.setName(registUser.getName());
            response.setLoginId(registUser.getLoginId());
            response.setNickname(registUser.getNickname());
        }

        return new ResponseEntity<RegistUserResponse>(response, httpStatus);
    }
}
