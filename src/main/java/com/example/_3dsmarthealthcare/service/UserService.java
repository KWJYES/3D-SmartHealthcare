package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.entity.User;
import com.example._3dsmarthealthcare.common.pojo.Response;

import java.util.Map;

public interface UserService extends IService<User> {
    Response login(Map dataMap);
    Response signup(User user, String captcha);
}
