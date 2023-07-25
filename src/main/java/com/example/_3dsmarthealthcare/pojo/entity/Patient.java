package com.example._3dsmarthealthcare.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Patient {
    @TableId(type = IdType.AUTO)
    public long id;
    public String name;
    public int sex;//1男0女
    public int age;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT",locale = "zh")
    public Date birthDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT",locale = "zh")
    public Date registrationDate;
    public long uid;

    public String phone;
    public String address;
    public String diagnosis;
}
