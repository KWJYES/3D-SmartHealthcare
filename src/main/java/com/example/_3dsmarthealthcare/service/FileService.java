package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.File;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileService extends IService<File> {
    ResponseResult<?> uploadNii(MultipartFile[] file, HttpServletRequest request);
    ResponseResult<?> uploadPdf(MultipartFile file, HttpServletRequest request);

    void requestStaticResources(String key, HttpServletRequest request, HttpServletResponse response);

    ResponseResult<?> getFiles(int typ, int page, int size);

    ResponseResult<?> getDetail(long fileId, HttpServletRequest request);
    ResponseResult<?> deleteFile(List<Long> fileId);

    List<File> findNiiFileByIds(List<Integer> fileIds);

    List<File> getNiiByPid(Long pid);

    ResponseResult<?> findPdfByPid(int pid);

    List<File> findMarkFileByIds(List<Integer> unreasoningNiiIds);

    List<File> getMarkNiiByPid(Long pid);
}
