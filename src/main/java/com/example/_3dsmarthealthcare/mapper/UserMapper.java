package com.example._3dsmarthealthcare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example._3dsmarthealthcare.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
