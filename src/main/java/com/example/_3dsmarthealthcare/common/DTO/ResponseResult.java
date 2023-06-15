package com.example._3dsmarthealthcare.common.DTO;

import lombok.Data;

@Data
public class ResponseResult<T> {
    private int code;
    private String msg;
    private T data;

    public ResponseResult(Msg msg, T data) {
        this.code = msg.code;
        this.msg = msg.msg;
        this.data = data;
    }

    public ResponseResult(Msg msg) {
        this.code = msg.code;
        this.msg = msg.msg;
    }

    public static ResponseResult<?> success() {
        return new ResponseResult<>(new Msg(Msg.success, "success"));
    }

    public static ResponseResult<?> success(String msg) {
        return new ResponseResult<>(new Msg(Msg.success, msg));
    }

    public static ResponseResult<?> success(String msg,Object obj) {
        return new ResponseResult<>(new Msg(Msg.success, msg),obj);
    }

    public static ResponseResult<?> failure() {
        return new ResponseResult<>(new Msg(Msg.failure, "failure"));
    }

    public static ResponseResult<?> failure(String msg) {
        return new ResponseResult<>(new Msg(Msg.failure, msg));
    }

    public static ResponseResult<?> failure(int msgCode, String msg) {
        return new ResponseResult<>(new Msg(msgCode, msg));
    }
}
