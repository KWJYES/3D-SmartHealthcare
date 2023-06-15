package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.DTO.ResponseResult;
import com.example._3dsmarthealthcare.common.util.FileUtil;
import com.example._3dsmarthealthcare.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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

    @GetMapping("/{key:^.*\\.pdf|^.*\\.inn|^.*\\.jpg|^.*\\.png$}")
    public void requestStaticResources(@PathVariable String key, HttpServletRequest request, HttpServletResponse response){
        fileService.requestStaticResources(key,request,response);
    }

    @GetMapping("/pdf")
    public ResponseResult<?> getPdfFile(@RequestParam int page,@RequestParam int size){
        return fileService.getFiles(FileUtil.pdf,page,size);
    }

    @GetMapping("/inn")
    public ResponseResult<?> getPInnFile(@RequestParam int page,@RequestParam int size){
        return fileService.getFiles(FileUtil.inn,page,size);
    }

    @GetMapping("/detail")
    public ResponseResult<?> getFileDetail(@RequestParam long fileId, HttpServletRequest request){
        return fileService.getDetail(fileId, request);
    }
}
