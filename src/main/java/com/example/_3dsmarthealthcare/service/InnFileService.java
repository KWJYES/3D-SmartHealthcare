package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import com.example._3dsmarthealthcare.entity.InnFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface InnFileService extends IService<InnFile> {
    ResponseResult<?> uploadInnFile(MultipartFile file, HttpServletRequest request);
}
