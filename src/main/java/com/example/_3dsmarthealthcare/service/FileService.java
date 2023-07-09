package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.File;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface FileService extends IService<File> {
    ResponseResult<?> uploadNii(MultipartFile file, HttpServletRequest request);
    ResponseResult<?> uploadPdf(MultipartFile file, HttpServletRequest request);

    void requestStaticResources(String key, HttpServletRequest request, HttpServletResponse response);

    ResponseResult<?> getFiles(int typ, int page, int size);

    ResponseResult<?> getDetail(long fileId, HttpServletRequest request);
}
