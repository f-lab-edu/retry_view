package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String mainIndex(){
        String result = "";
        for(UserDTO user : userService.getUserList()){
            result += user;
        }
        return "MainController : "+result;
    }
}
