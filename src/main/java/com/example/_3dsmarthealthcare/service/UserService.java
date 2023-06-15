package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.entity.User;
import com.example._3dsmarthealthcare.common.DTO.ResponseResult;

import java.util.Map;

public interface UserService extends IService<User> {
    ResponseResult<?> login(Map<String, String> dataMap);
    ResponseResult<?> signup(User user, String captcha);
}
