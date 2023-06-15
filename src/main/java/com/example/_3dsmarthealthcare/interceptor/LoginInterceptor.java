package com.example._3dsmarthealthcare.interceptor;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example._3dsmarthealthcare.common.Context;
import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.common.DTO.Msg;
import com.example._3dsmarthealthcare.common.DTO.ResponseResult;
import com.example._3dsmarthealthcare.common.util.RedisUtil;
import com.google.gson.Gson;
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
    private RedisUtil redisUtil = Context.getBean(RedisUtil.class);
    ;

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
        redisUtil.expire("user_token" + token, 30, TimeUnit.MINUTES);
        //登陆认证成功，保存userId,以便在controller或service中使用
        UserIdThreadLocal.set(userId);
        // 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*tomcat底层 每一个请求都是一个线程，如果每一个请求都启动一个线程，性能就会降低，
        1. 于是就有了线程池，而线程池中的线程并不是真正销毁或真正启动的。
        2. 也就是说这个请求的线程是个可复用的线程，第二次请求可能还会拿到刚刚的线程，
        3. 若不清空，里面本身就有user对象，数据会错乱*/
        UserIdThreadLocal.set(null); //清空本地线程中的user对象数据
    }

    private void returnNoLogin(HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("Application/json;charset=utf-8");
        // 构造返回响应体
        String resultString = new Gson().toJson(ResponseResult.failure(Msg.no_login, "未登陆"));
        outputStream.write(resultString.getBytes(StandardCharsets.UTF_8));
    }
}
