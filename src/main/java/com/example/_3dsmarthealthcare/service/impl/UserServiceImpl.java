package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.entity.User;
import com.example._3dsmarthealthcare.mapper.UserMapper;
import com.example._3dsmarthealthcare.pojo.Response;
import com.example._3dsmarthealthcare.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Response login(String email,String password){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail, email);
        User user=getOne(queryWrapper);
        if (user==null){
            return new Response(-201,"用户不存在");
        }else if(user.password.equals(password)){
            return new Response(200,"登陆成功",user);
        }else {
            return new Response(-202,"账号或密码错误");
        }
    }
}
