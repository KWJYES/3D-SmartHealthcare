package com.example._3dsmarthealthcare.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MaskItem {
    @TableId(type = IdType.AUTO)
    public  long id;
    public String url;
    public String path;
    public String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT",locale = "zh")
    public Date maskTime;
    public long uid;
    public long taskId;
    public long pid;
    public long taskItemId;

    public MaskItem(TaskItem taskItem) {
        this.name=taskItem.name;
        this.id = taskItem.id;
        this.url = taskItem.url.replace("/task_done/","/task_done/mask/");
        this.path = taskItem.path.replace("\\task_done\\","\\task_done\\mask\\");
        this.maskTime = new Date();
        this.uid = taskItem.uid;
        this.taskId = taskItem.taskId;
        this.pid = taskItem.pid;
        this.taskItemId = taskItem.id;
    }
}
