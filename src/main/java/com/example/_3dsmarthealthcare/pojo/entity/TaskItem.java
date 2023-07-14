package com.example._3dsmarthealthcare.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class TaskItem {
    @TableId(type = IdType.AUTO)
    public long id;
    public String path;
    public String url;
    public String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT",locale = "zh")
    public Date appendTime;
    public long uid;
    public long taskId;
    public long pid;
}
