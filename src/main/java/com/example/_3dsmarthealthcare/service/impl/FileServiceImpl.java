package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.common.pojo.Msg;
import com.example._3dsmarthealthcare.common.pojo.ResponseResult;
import com.example._3dsmarthealthcare.common.util.FileUtil;
import com.example._3dsmarthealthcare.entity.InnFile;
import com.example._3dsmarthealthcare.mapper.InnFileMapper;
import com.example._3dsmarthealthcare.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<InnFileMapper, InnFile> implements FileService {
    @Autowired
    private FileUtil fileUtil;

    @Override
    public ResponseResult<?> uploadInn(MultipartFile file, HttpServletRequest request){
        String fn=file.getOriginalFilename();
        if (fn==null)
            return ResponseResult.failure("上传失败,上传文件名不能为空");
        if (!fn.endsWith(".inn"))
            return ResponseResult.failure(Msg.file_type_error,"上传失败上传的不是inn文件");
        String url=fileUtil.saveFile(UserIdThreadLocal.get(),FileUtil.inn,file,request);
        if(url==null)
            return ResponseResult.failure("uploadInnFileIO异常");
        return ResponseResult.success("上传成功", url);
    }

    @Override
    public ResponseResult<?> uploadPdf(MultipartFile file, HttpServletRequest request) {
        String fn=file.getOriginalFilename();
        if (fn==null)
            return ResponseResult.failure("上传失败,上传文件名不能为空");
        if (!fn.endsWith(".pdf"))
            return ResponseResult.failure(Msg.file_type_error,"上传失败上传的不是pdf文件");
        String url=fileUtil.saveFile(UserIdThreadLocal.get(),FileUtil.pdf,file,request);
        if(url==null)
            return ResponseResult.failure("uploadInnFileIO异常");
        return ResponseResult.success("上传成功", url);
    }
}
