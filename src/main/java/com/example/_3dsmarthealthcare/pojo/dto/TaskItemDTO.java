package com.example._3dsmarthealthcare.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class TaskItemDTO {
    public long id;
    public String url;
    public String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT",locale = "zh")
    public Date appendTime;
    public long uid;
    public long taskId;

    public TaskItemDTO(long id, String url, String name, Date appendTime, long uid, long taskId) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.appendTime = appendTime;
        this.uid = uid;
        this.taskId = taskId;
    }
}
