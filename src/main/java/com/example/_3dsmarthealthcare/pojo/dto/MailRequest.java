package com.example._3dsmarthealthcare.pojo.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class MailRequest implements Serializable {
    /**
     * 接收人
     */
    private String sendTo;

    /**
     *  邮件主题
     */
    private String subject;

    /**
     *  邮件内容
     */
    private String text;

    /**
     *  附件路径
     */
    private String filePath;

}


