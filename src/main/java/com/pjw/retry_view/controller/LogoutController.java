package com.pjw.retry_view.controller;

import com.pjw.retry_view.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    private final UserService userService;

    public LogoutController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void logout(HttpServletRequest req){
        String loginId = req.getAttribute("loginId").toString();
        userService.logout(loginId);
    }
}
