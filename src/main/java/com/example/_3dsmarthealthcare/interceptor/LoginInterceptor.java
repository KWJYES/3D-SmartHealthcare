package com.example._3dsmarthealthcare.interceptor;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example._3dsmarthealthcare.common.Context;
import com.example._3dsmarthealthcare.common.pojo.Msg;
import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import com.example._3dsmarthealthcare.common.util.RedisUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {
//    @Autowired
    private RedisUtil redisUtil= Context.getBean(RedisUtil.class);;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            returnNoLogin(response);
            return false;
        }
        // 从redis中拿token对应userId
        String userId = redisUtil.get("user_token" + token);
        if (userId == null) {
            returnNoLogin(response);
            return false;
        }
        // token续期
        redisUtil.expire("user_token" + token,30, TimeUnit.MINUTES);
        // 放行
        return true;
    }

    private void returnNoLogin(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("Application/json;charset=utf-8");
        // 构造返回响应体
        String resultString = new Gson().toJson(ResponseResult.failure(Msg.no_login,"未登陆"));
        outputStream.write(resultString.getBytes(StandardCharsets.UTF_8));
    }
}
