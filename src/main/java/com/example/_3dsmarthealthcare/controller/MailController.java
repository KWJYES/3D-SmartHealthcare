package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.pojo.Msg;
import com.example._3dsmarthealthcare.common.pojo.Response;
import com.example._3dsmarthealthcare.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private SendMailService sendMailService;

    @GetMapping("/getCaptcha")
    public Response getCaptcha(@RequestParam(name = "email") String email) {
        if (email.equals("")) return new Response(new Msg(Msg.param_is_null, "email_is_null"));
        sendMailService.sendCaptcha(email);
        return new Response(new Msg(Msg.success, "验证码已发送"));
    }

}

