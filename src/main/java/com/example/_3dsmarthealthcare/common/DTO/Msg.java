package com.example._3dsmarthealthcare.common.DTO;

public class Msg {
    public int code;
    public String msg;
    public final static int success =200;//成功
    public final static int failure =500;//失败
    public final static int user_not_exist=-201;//用户不存在
    public final static int account_or_password_error =-202;//账号或密码错误
    public final static int request_body_or_param_error=-203;//请求体或请求参数不正确
    public final static int user_exist=-204;//用户已存在
    public final static int param_is_null=-205;//参数为空
    public final static int captcha_expired =-206;//验证码过期
    public final static int captcha_error=-207;//验证码错误
    public final static int no_login=-208;//未登陆
    public final static int file_type_error=-209;//上传的文件格式不匹配

    public Msg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
