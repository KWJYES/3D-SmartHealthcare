package com.example._3dsmarthealthcare.pojo.dto;

import com.example._3dsmarthealthcare.pojo.entity.File;
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

    public FileDTO(File file) {
        this.id = file.id;
        this.uid = file.uid;
        this.type = file.type;
        this.name = file.name;
        this.upload_time = file.uploadTime;
    }
}
