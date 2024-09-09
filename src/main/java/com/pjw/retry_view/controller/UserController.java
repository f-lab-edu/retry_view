package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public String getUsers(){
        String result = "";
        for(UserDTO user : userService.getUserList()){
            result += user;
        }
        return "MainController : "+result;
    }

}
