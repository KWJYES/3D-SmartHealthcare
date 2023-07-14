package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;
    @GetMapping("/hello")
    public ResponseResult<?> hello(){
        return ResponseResult.success();
    }
}
