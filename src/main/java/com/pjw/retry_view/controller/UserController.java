package com.pjw.retry_view.controller;

import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.service.UserService;
import com.pjw.retry_view.validator.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserValidator userValidator;

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(userValidator);
    }

    @GetMapping
    public String getUsers(){
        String result = "";
        for(UserDTO user : userService.getUserList()){
            result += user;
        }
        return "MainController : "+result;
    }

    @PostMapping
    public Map<String, Object> registUser(@RequestBody @Validated UserDTO user, BindingResult bindingResult){
        Map<String, Object> result = new HashMap<>();

        if(bindingResult.hasErrors()){
            result.put("msg", "failure");
            result.put("result", bindingResult.getAllErrors());
        }else{
            result.put("msg","success");
            result.put("result", userService.insertUser(user));
        }

        return result;
    }
}
