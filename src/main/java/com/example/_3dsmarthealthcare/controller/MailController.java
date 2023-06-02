package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.pojo.Msg;
import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import com.example._3dsmarthealthcare.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private SendMailService sendMailService;

    @GetMapping("/getCaptcha")
    public ResponseResult<?> getCaptcha(@RequestParam(name = "email") String email) {
        if (email.equals("")) return ResponseResult.failure(Msg.param_is_null, "email is null");
        sendMailService.sendCaptcha(email);
        return ResponseResult.success("验证码已发送");
    }

}

