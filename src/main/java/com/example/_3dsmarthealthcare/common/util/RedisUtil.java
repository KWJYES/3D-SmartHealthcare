package com.example._3dsmarthealthcare.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    public void set(String key,String val,int cacheTime,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,val,cacheTime, timeUnit);
    }

    public String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void expire(String key,int cacheTime,TimeUnit timeUnit){
        redisTemplate.expire(key, cacheTime,timeUnit);
    }

    public void remove(String key){
        redisTemplate.delete(key);
    }
}
