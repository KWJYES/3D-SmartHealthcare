package com.example._3dsmarthealthcare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    public long user_id;
    public String email;
    public String password;
    public String user_name;
    public String name;
    public String sex;
}
