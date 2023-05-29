package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.pojo.Response;
import com.example._3dsmarthealthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/signUp")
    public Response signUp(){
        return null;
    }

    @PostMapping("/login")
    public Response login(String email,String password){
        return service.login(email,password);
    }
}
