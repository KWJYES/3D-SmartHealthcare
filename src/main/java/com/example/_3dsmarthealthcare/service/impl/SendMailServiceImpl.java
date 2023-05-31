package com.example._3dsmarthealthcare.service.impl;

import com.example._3dsmarthealthcare.common.pojo.MailRequest;
import com.example._3dsmarthealthcare.common.util.MailUtil;
import com.example._3dsmarthealthcare.common.util.RedisUtil;
import com.example._3dsmarthealthcare.service.SendMailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.concurrent.TimeUnit;

@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void sendCaptcha(String to) {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("3D-智慧医疗验证码");
        mailRequest.setSendTo(to);
        Context context=new Context();
        int randomCode = (int) ((Math.random() * 8 + 1) * 100000);
        redisUtil.set("captcha_for_"+to,randomCode+"",10, TimeUnit.MINUTES);//存入redis并设置缓存时间
        context.setVariable("code",randomCode+"");
        mailUtil.sendTemplateMail(mailRequest,context,"email_captcha");
    }
}


