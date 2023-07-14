package com.example._3dsmarthealthcare.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example._3dsmarthealthcare.pojo.entity.MaskItem;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class MaskItemDTO {
    @TableId(type = IdType.AUTO)
    public  long id;
    public String url;
    public String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT",locale = "zh")
    public Date maskTime;
    public long uid;
    public long taskId;
    public long pid;
    public long taskItemId;

    public MaskItemDTO(MaskItem maskItem) {
        this.id = maskItem.id;
        this.url = maskItem.url;
        this.name = maskItem.name;
        this.maskTime = maskItem.maskTime;
        this.uid = maskItem.uid;
        this.taskId = maskItem.taskId;
        this.pid = maskItem.pid;
        this.taskItemId = maskItem.taskItemId;
    }
}
