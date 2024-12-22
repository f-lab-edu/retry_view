package com.pjw.retry_view.controller;

import com.pjw.retry_view.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "로그아웃 API 컨트롤러", description = "로그아웃 API")
@Controller
@RequestMapping("/logout")
public class LogoutController {
    private final UserService userService;

    public LogoutController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "일반 로그아웃 API", description = "")
    @PostMapping
    public void logout(HttpServletRequest req){
        String loginId = req.getAttribute("loginId").toString();
        userService.logout(loginId);
    }
}
