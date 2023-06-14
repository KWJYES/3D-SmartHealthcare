package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import com.example._3dsmarthealthcare.entity.InnFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface FileService extends IService<InnFile> {
    ResponseResult<?> uploadInn(MultipartFile file, HttpServletRequest request);
    ResponseResult<?> uploadPdf(MultipartFile file, HttpServletRequest request);
}
