package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import com.example._3dsmarthealthcare.service.InnFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/inn")
public class InnFileController {
    @Autowired
    private InnFileService innFileService;
    @PostMapping("/upload")
    public ResponseResult<?> uploadInnFile(MultipartFile file, HttpServletRequest request) {
        return innFileService.uploadInnFile(file,request);
    }
}
