package com.example._3dsmarthealthcare.common.DTO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class FileDTO {
    public long id;
    public long uid;
    public int type;//inn 0，img 1，pdf 2
    public String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT",locale = "zh")
    public Date upload_time;

    public FileDTO(long id, long uid, int type, String name, Date upload_time) {
        this.id = id;
        this.uid = uid;
        this.type = type;
        this.name = name;
        this.upload_time = upload_time;
    }
}
