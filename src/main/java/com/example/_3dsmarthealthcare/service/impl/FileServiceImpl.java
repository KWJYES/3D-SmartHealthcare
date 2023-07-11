package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.pojo.dto.FileDTO;
import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.pojo.dto.Msg;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.common.util.FileUtil;
import com.example._3dsmarthealthcare.common.util.RedisUtil;
import com.example._3dsmarthealthcare.pojo.entity.File;
import com.example._3dsmarthealthcare.mapper.NiiFileMapper;
import com.example._3dsmarthealthcare.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class FileServiceImpl extends ServiceImpl<NiiFileMapper, File> implements FileService {
    @Value(("${file-save-path}"))
    private String fileSavePath;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResponseResult<?> uploadNii(MultipartFile[] files, HttpServletRequest request) {
        List<String> urlList = new ArrayList<>();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file";
        for (MultipartFile file : files) {
            String fn = file.getOriginalFilename();
            if (fn == null)
                return ResponseResult.failure("上传失败,上传文件名不能为空");
            if (!(fn.endsWith(".nii") || fn.endsWith(".nii.gz")))
                return ResponseResult.failure(Msg.file_type_error, "上传失败上传的不是nii或nii.gz文件");
            //生成url
            String url = fileUtil.saveFile(UserIdThreadLocal.get(), FileUtil.niiStr, file, request);
            if (url == null)
                return ResponseResult.failure("uploadNiiFileIO异常");
            //保存到数据库
            File niiFile = new File();
            niiFile.uid = Long.parseLong(UserIdThreadLocal.get());
            niiFile.uploadTime = new Date();
            niiFile.name = fn;
            niiFile.url = url;
            niiFile.path = url.replace(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/", fileSavePath);
            niiFile.type = FileUtil.nii;
            save(niiFile);
            //生成动态url
            String dynamicUrl = fileUtil.getDynamicUrl(UserIdThreadLocal.get(), fn.substring(fn.lastIndexOf(".")));
            //用redis设置动态url有效时间为30min
            redisUtil.set(dynamicUrl, url, 2, TimeUnit.HOURS);
            urlList.add(baseUrl + "/" + dynamicUrl);
        }
        return ResponseResult.success("上传成功", urlList);
    }

    @Override
    public ResponseResult<?> uploadPdf(MultipartFile file, HttpServletRequest request) {
        String fn = file.getOriginalFilename();
        if (fn == null)
            return ResponseResult.failure("上传失败,上传文件名不能为空");
        if (!fn.endsWith(".pdf"))
            return ResponseResult.failure(Msg.file_type_error, "上传失败上传的不是pdf文件");
        String url = fileUtil.saveFile(UserIdThreadLocal.get(), FileUtil.pdfStr, file, request);
        if (url == null)
            return ResponseResult.failure("uploadPdfFileIO异常");
        //保存到数据库
        File pdfFile = new File();
        pdfFile.uid = Long.parseLong(UserIdThreadLocal.get());
        pdfFile.uploadTime = new Date();
        pdfFile.name = fn;
        pdfFile.url = url;
        pdfFile.path = url.replace(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/", fileSavePath);
        pdfFile.type = FileUtil.pdf;
        save(pdfFile);
        //生成动态url
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file";
        String dynamicUrl = fileUtil.getDynamicUrl(UserIdThreadLocal.get(), fn.substring(fn.lastIndexOf(".")));
        //用redis设置动态url有效时间为30min
        redisUtil.set(dynamicUrl, url, 2, TimeUnit.HOURS);
        return ResponseResult.success("上传成功", baseUrl + "/" + dynamicUrl);
    }

    @Override
    public void requestStaticResources(String key, HttpServletRequest request, HttpServletResponse response) {
        String url = redisUtil.get(key);
        if (url == null)
            return;
        try {
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            request.getRequestDispatcher(url.replace(baseUrl, "")).forward(request, response);
        } catch (ServletException | IOException e) {
            log.error("重定向静态资源失败");
        }
    }

    @Override
    public ResponseResult<?> getFiles(int type, int page, int size) {
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(File::getUid, UserIdThreadLocal.get());
        Page<File> page1 = new Page<>(page, size);
        IPage<File> iPage = page(page1, queryWrapper);
        List<File> fileList = iPage.getRecords();
        List<FileDTO> fileDTOList = new ArrayList<>();
        for (File file : fileList) {
            fileDTOList.add(new FileDTO(file));
        }
        return ResponseResult.success("success", fileDTOList);
    }

    @Override
    public ResponseResult<?> getDetail(long fileId, HttpServletRequest request) {
        LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(File::getId, fileId);
        File file = getOne(queryWrapper);
        //生成动态url
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file";
        String dynamicUrl = fileUtil.getDynamicUrl(UserIdThreadLocal.get(), file.name.substring(file.name.lastIndexOf(".")));
        //用redis设置动态url有效时间为30min
        redisUtil.set(dynamicUrl, file.url, 2, TimeUnit.HOURS);
        HashMap<String, Object> dataMap = new HashMap<>();
        FileDTO fileDTO = new FileDTO(file);
        dataMap.put("file", fileDTO);
        dataMap.put("url", baseUrl + "/" + dynamicUrl);
        return ResponseResult.success("success", dataMap);
    }

    @Override
    public ResponseResult<?> deleteFile(List<Long> fileId) {
        String uid = UserIdThreadLocal.get();
        for (Long id : fileId) {
            LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(File::getId, id).eq(File::getUid, Long.parseLong(uid));
            FileUtils.deleteQuietly(new java.io.File(getOne(queryWrapper).path));
            baseMapper.delete(queryWrapper);
        }
        return ResponseResult.success();
    }

    @Override
    public List<File> findFileByIds(List<Integer> fileIds) {
        List<File> fileList = new ArrayList<>();
        String uid = UserIdThreadLocal.get();
        for (Integer fileId : fileIds) {
            Long id = Long.parseLong(String.valueOf(fileId));
            LambdaQueryWrapper<File> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(File::getId, id).eq(File::getUid, Long.parseLong(uid));
            fileList.add(getOne(queryWrapper));
        }
        return fileList;
    }
}
