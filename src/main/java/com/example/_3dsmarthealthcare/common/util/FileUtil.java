package com.example._3dsmarthealthcare.common.util;

import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class FileUtil {
    @Value(("${file-save-path}"))
    private String fileSavePath;
    public static final String inn="/inn/";
    public static final String img="/img/";
    public static final String pdf="/pdf/";


    /**
     * 保存前端上传的图片，并返回文件url
     * @param userId
     * @param file
     * @param fileType 文件类型
     * @param request
     * @return 文件url
     */
    public String saveFile(String userId,String fileType, MultipartFile file, HttpServletRequest request) {
        //1.后半段目录：
        String directory ="userId_"+ userId + "/";
        /**
         *  2.文件保存目录  ${file-save-path}
         *  如果目录不存在，则创建
         */
        File dir = new File(fileSavePath+fileType + directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        log.info("文件上传，保存位置：" + fileSavePath+fileType + directory);
        //3.给文件重新设置一个名字
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));//后缀
        log.info("文件后缀：{}", suffix);
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        //4.创建这个新文件
        File newFile = new File(fileSavePath+fileType + directory + newFileName);
        //5.复制操作
        try {
            file.transferTo(newFile);
            //协议 :// ip地址 ：端口号 / 文件目录(/fileType/userId/xxx.inn)
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + fileType + directory + newFileName;
            log.info("文件上传，访问URL：" + url);
            return url;
        } catch (IOException e) {
            return null;
        }
    }
}
