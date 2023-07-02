package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.DTO.ResponseResult;
import com.example._3dsmarthealthcare.entity.User;
import com.example._3dsmarthealthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signUp")
    public ResponseResult<?> signUp(@RequestBody User user, @RequestParam(name = "captcha") String captcha) {
        return userService.signup(user,captcha);
    }

    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody Map dataMap) {
        return userService.login(dataMap);
    }
}


