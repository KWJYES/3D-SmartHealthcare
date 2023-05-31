package com.example._3dsmarthealthcare.common.pojo;

import lombok.Data;

@Data
public class Response<T>{
    private int code;
    private String msg;
    private T data;

    public Response(Msg msg, T data) {
        this.code = msg.code;
        this.msg = msg.msg;
        this.data = data;
    }

    public Response(Msg msg) {
        this.code = msg.code;
        this.msg = msg.msg;
    }
}
