package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.common.util.FileUtil;
import com.example._3dsmarthealthcare.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/uploadNii")
    public ResponseResult<?> uploadNii(MultipartFile[] files, HttpServletRequest request) {
        return fileService.uploadNii(files, request);
    }

    @PostMapping("/uploadPdf")
    public ResponseResult<?> uploadPdf(MultipartFile file, HttpServletRequest request) {
        return fileService.uploadPdf(file, request);
    }

    @GetMapping("/{key:^.*\\.pdf|.*\\.nii|.*\\.gz|.*\\.jpg|.*\\.png$}")
//    @GetMapping("/{key}")
    public void requestStaticResources(@PathVariable String key, HttpServletRequest request, HttpServletResponse response){
        fileService.requestStaticResources(key,request,response);
    }

    @GetMapping("/pdf")
    public ResponseResult<?> getPdfFile(@RequestParam int page,@RequestParam int size){
        return fileService.getFiles(FileUtil.pdf,page,size);
    }

    @GetMapping("/nii")
    public ResponseResult<?> getNiiFile(@RequestParam int page, @RequestParam int size){
        return fileService.getFiles(FileUtil.nii,page,size);
    }

    @GetMapping("/detail")
    public ResponseResult<?> getFileDetail(@RequestParam long fileId, HttpServletRequest request){
        return fileService.getDetail(fileId, request);
    }
    @PostMapping("/delete")
    public ResponseResult<?> deleteFile(@RequestBody List<Long> fileIds){
        return fileService.deleteFile(fileIds);
    }
}
