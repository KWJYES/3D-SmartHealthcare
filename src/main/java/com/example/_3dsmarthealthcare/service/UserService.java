package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.entity.User;
import com.example._3dsmarthealthcare.pojo.Response;

public interface UserService extends IService<User> {
    public Response login(String email, String password);
}
