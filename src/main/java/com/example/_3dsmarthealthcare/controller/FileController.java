package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {
    @Value(("${file-save-path}"))
    private String fileSavePath;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd/");

    @PostMapping("/uploadInn")
    public ResponseResult<?> uploadInnFile(MultipartFile file, HttpServletRequest request) {
        //1.后半段目录：  2020/03/15
        String directory = simpleDateFormat.format(new Date());
        /**
         *  2.文件保存目录  ${file-save-path}
         *  如果目录不存在，则创建
         */
        File dir = new File(fileSavePath + directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        log.info("文件上传，保存位置：" + fileSavePath + directory);
        //3.给文件重新设置一个名字
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));//后缀
        log.info("文件后缀：{}", suffix);
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        //4.创建这个新文件
        File newFile = new File(fileSavePath + directory + newFileName);
        //5.复制操作
        try {
            file.transferTo(newFile);
            //协议 :// ip地址 ：端口号 / 文件目录(/inn/2023-xx-xx/xxx.inn)
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/inn/" + directory + newFileName;
            log.info("图片上传，访问URL：" + url);
            return ResponseResult.success("上传成功", url);
        } catch (IOException e) {
            return ResponseResult.failure("IO异常");
        }
    }
}
