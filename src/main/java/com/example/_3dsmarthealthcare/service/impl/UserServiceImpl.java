package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.common.pojo.Msg;
import com.example._3dsmarthealthcare.common.util.RedisUtil;
import com.example._3dsmarthealthcare.entity.User;
import com.example._3dsmarthealthcare.mapper.UserMapper;
import com.example._3dsmarthealthcare.common.pojo.Response;
import com.example._3dsmarthealthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Response login(Map dataMap) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        String email = (String) dataMap.getOrDefault("email", "");
        String password = (String) dataMap.getOrDefault("password", "");
        if (email.equals("") || password.equals(""))
            return new Response(new Msg(Msg.request_body_or_param_error, "request_body_or_param_error"));
        queryWrapper.eq(User::getEmail, email);
        User user = getOne(queryWrapper);
        if (user == null) {
            return new Response(new Msg(Msg.user_not_exist, "user_not_exist"));
        } else if (user.password.equals(password)) {
            return new Response(new Msg(Msg.success, "login_success"), user);
        } else {
            return new Response(new Msg(Msg.account_or_password_error, "account_or_password_error"));
        }
    }

    @Override
    public Response signup(User user, String captcha) {
        String captchaVal=redisUtil.get("captcha_for_"+user.email);//查看redis缓存
        if (captchaVal==null) return new Response(new Msg(Msg.captcha_expired,"captcha expired"));
        if (!captchaVal.equals(captcha)) return new Response(new Msg(Msg.captcha_error,"captcha error"));
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, user.email);
        User user1 = getOne(queryWrapper);
        if(user1!=null){
            return new Response(new Msg(Msg.user_exist, "user_exist"));
        }else {
            redisUtil.remove("captcha_for_"+user.email);//清除redis缓存
            save(user);
            return new Response(new Msg(Msg.success, "sign_up_success"));
        }
    }
}
