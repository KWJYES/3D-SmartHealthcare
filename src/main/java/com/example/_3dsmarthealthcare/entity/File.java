package com.example._3dsmarthealthcare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class File {
    @TableId(type = IdType.AUTO)
    public long id;
    public long uid;
    public String url;
    public String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT",locale = "zh")
    public Date upload_time;
    public int type;
}
