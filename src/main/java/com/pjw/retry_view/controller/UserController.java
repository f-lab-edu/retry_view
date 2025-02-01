package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.UserDetail;
import com.pjw.retry_view.enums.ApiResponseCodeExamples;
import com.pjw.retry_view.enums.ErrorCode;
import com.pjw.retry_view.enums.UserAuth;
import com.pjw.retry_view.dto.UserView;
import com.pjw.retry_view.request.RegistUserRequest;
import com.pjw.retry_view.response.RegistUserResponse;
import com.pjw.retry_view.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Tag(name = "유저 관리 API 컨트롤러", description = "유저 관리 API")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "유저 목록 조회 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.INVALID_TOKEN})
    @GetMapping
    public String getUsers() {
        return "MainController : " + userService.getUserList().stream().map(UserView::toString).collect(Collectors.joining(", "));
    }

    @Operation(summary = "일반 회원가입 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.DUPLICATE_REQ})
    @PostMapping("/regist")
    public RegistUserResponse registUser(@RequestBody @Valid RegistUserRequest userReq, BindingResult bindingResult) {
        RegistUserResponse response = new RegistUserResponse();

        if (bindingResult.hasErrors()) {
            response.setBindingErrors(bindingResult.getAllErrors());
        } else {
            UserView user = userReq.toUserDTO();
            user.setRole(UserAuth.USER);
            UserView registUser = userService.registUser(user);
            response.setName(registUser.getName());
            response.setLoginId(registUser.getLoginId());
            response.setNickname(registUser.getNickname());
        }

        return response;
    }

    @Operation(summary = "일반 탈퇴 API", description = "")
    @ApiResponseCodeExamples({ErrorCode.USER_NOT_FOUND, ErrorCode.INVALID_TOKEN, ErrorCode.DUPLICATE_REQ})
    @DeleteMapping("/withdraw")
    public ResponseEntity<String> withdrawUser(@AuthenticationPrincipal UserDetail userDetail){
        String loginId = userDetail.getUsername();
        userService.withdrawUser(loginId);
        return ResponseEntity.ok("Success");
    }
}
