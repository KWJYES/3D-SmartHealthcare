package com.example._3dsmarthealthcare.common.pojo;

public class Msg {
    public int code;
    public String msg;
    public final static int success=200;
    public final static int user_not_exist=-201;
    public final static int account_or_password_error =-202;
    public final static int request_body_or_param_error=-203;
    public final static int user_exist=-204;
    public final static int param_is_null=-205;
    public final static int captcha_expired =-206;
    public final static int captcha_error=-207;

    public Msg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
