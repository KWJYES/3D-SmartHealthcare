package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.common.pojo.Msg;
import com.example._3dsmarthealthcare.common.util.RedisUtil;
import com.example._3dsmarthealthcare.entity.User;
import com.example._3dsmarthealthcare.mapper.UserMapper;
import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import com.example._3dsmarthealthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResponseResult<?> login(Map<String, String> dataMap) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        String email = dataMap.getOrDefault("email", "");
        String password = dataMap.getOrDefault("password", "");
        if (email.equals("") || password.equals(""))
            return ResponseResult.failure(Msg.request_body_or_param_error, "request body or param error");
        queryWrapper.eq(User::getEmail, email);
        User user = getOne(queryWrapper);
        if (user == null) {
            return ResponseResult.failure(Msg.user_not_exist, "user not exist");
        } else if (user.password.equals(password)) {
            return ResponseResult.success("login success", user);
        } else {
            return ResponseResult.failure(Msg.account_or_password_error, "account or password error");
        }
    }

    @Override
    public ResponseResult<?> signup(User user, String captcha) {
        String captchaVal = redisUtil.get("captcha_for_" + user.email);//查看redis缓存
        if (captchaVal == null) return ResponseResult.failure(Msg.captcha_expired, "captcha expired");
        if (!captchaVal.equals(captcha)) return ResponseResult.failure(Msg.captcha_error, "captcha error");
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, user.email);
        User user1 = getOne(queryWrapper);
        if (user1 != null) {
            return ResponseResult.failure(Msg.user_exist, "user exist");
        } else {
            redisUtil.remove("captcha_for_" + user.email);//清除redis缓存
            save(user);
            return ResponseResult.success("sign up success");
        }
    }
}
