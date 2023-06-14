package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import com.example._3dsmarthealthcare.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/uploadInn")
    public ResponseResult<?> uploadInn(MultipartFile file, HttpServletRequest request) {
        return fileService.uploadInn(file, request);
    }

    @PostMapping("/uploadPdf")
    public ResponseResult<?> uploadPdf(MultipartFile file, HttpServletRequest request) {
        return fileService.uploadPdf(file, request);
    }
}
