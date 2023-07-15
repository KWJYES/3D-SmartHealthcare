package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.common.util.FileUtil;
import com.example._3dsmarthealthcare.pojo.dto.FormdaDTO;
import com.example._3dsmarthealthcare.pojo.dto.MaskItemDTO;
import com.example._3dsmarthealthcare.pojo.dto.PrepareDTO;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.MaskItem;
import com.example._3dsmarthealthcare.pojo.entity.Task;
import com.example._3dsmarthealthcare.pojo.entity.TaskItem;
import com.example._3dsmarthealthcare.service.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/dataProcessing")
public class DataProcessingController {
    @Value(("${file-save-path}"))
    private String fileSavePath;
    @Autowired
    private DataProcessingService dataProcessingService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskItemService taskItemService;
    @Autowired
    private MaskItemService maskItemService;

    @Autowired
    private FileService fileService;

    @PostMapping("/prepare")
    public ResponseResult<?> prepare(@RequestBody HashMap<String, Object> dataMpa) {
        Integer taskId = (Integer) dataMpa.get("taskId");
        Integer taskItemId = (Integer) dataMpa.get("taskItemId");
        if (taskItemId == null || taskId == null)
            return ResponseResult.failure("taskId或taskItemId为空");
        Task task = taskService.findTaskById(Long.parseLong(String.valueOf(taskId)));
        if (task == null)
            return ResponseResult.failure("不存在taskId为" + taskId + "的任务");
        if (task.isReasoning == 0)
            return ResponseResult.failure("taskId为" + taskId + "的任务还没有推理，请先推理");
        TaskItem taskItem = taskItemService.getTaskItemById(Long.parseLong(String.valueOf(taskItemId)));
        if (taskItem == null)
            return ResponseResult.failure("不存在taskItemId为" + taskId + "的.inn.gz");
        String image_path = taskItem.path;
        String res = dataProcessingService.prepare(image_path);
        log.info("flask返回{}", res);
        PrepareDTO prepareDTO = new Gson().fromJson(res, PrepareDTO.class);
        return ResponseResult.success("ok", prepareDTO);
    }

    @PostMapping("/mask")
    public ResponseResult<?> mask(@RequestBody HashMap<String, Object> dataMpa) {
        Integer taskId = (Integer) dataMpa.get("taskId");
        Integer taskItemId = (Integer) dataMpa.get("taskItemId");
        if (taskItemId == null || taskId == null)
            return ResponseResult.failure("taskId或taskItemId为空");
        Task task = taskService.findTaskById(Long.parseLong(String.valueOf(taskId)));
        if (task == null)
            return ResponseResult.failure("不存在taskId为" + taskId + "的任务");
        if (task.isReasoning == 0)
            return ResponseResult.failure("taskId为" + taskId + "的任务还没有推理，请先推理");
        TaskItem taskItem = taskItemService.getTaskItemById(Long.parseLong(String.valueOf(taskItemId)));
        if (taskItem == null)
            return ResponseResult.failure("不存在taskItemId为" + taskId + "的.inn.gz");
        String image_path = taskItem.path;
        String out_path = image_path.replace("\\task_done\\", "\\task_done\\mask\\");
        File dir = new File(out_path).getParentFile();
        if (!dir.exists())
            dir.mkdirs();
        String res = dataProcessingService.mask(image_path, out_path);
        if (res.equals("ok")) {
            MaskItem maskItem = new MaskItem();
            maskItem.name = taskItem.name;
            maskItem.id = taskItem.id;
            maskItem.url = taskItem.url.replace("/task_done/", "/task_done/mask/");
            maskItem.path = taskItem.path.replace("\\task_done\\", "\\task_done\\mask\\");
            maskItem.maskTime = new Date();
            maskItem.uid = taskItem.uid;
            maskItem.taskId = taskItem.taskId;
            maskItem.pid = taskItem.pid;
            maskItem.taskItemId = taskItem.id;
            //save
            maskItemService.save(maskItem);
            MaskItemDTO maskItemDTO = new MaskItemDTO(maskItem);
            return ResponseResult.success("ok", maskItemDTO);
        }
        return ResponseResult.failure(res);
    }

    @PostMapping("/merge")
    public ResponseResult<?> merge(@RequestParam MultipartFile file, @RequestParam int taskId, @RequestParam int taskItemId, HttpServletRequest request) {
        Task task = taskService.findTaskById(Long.parseLong(String.valueOf(taskId)));
        if (task == null)
            return ResponseResult.failure("任务不存在，请传正确的taskId");
        if (task.isReasoning == 0)
            ResponseResult.failure("该任务未推理");
        TaskItem taskItem = taskItemService.getTaskItemById(Long.parseLong(String.valueOf(taskItemId)));
        if (taskItem == null)
            return ResponseResult.failure("taskItem不存在，请传正确的taskItemId");


        File dir = new File(fileSavePath + "temp\\");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        log.info("文件上传，保存位置：" + fileSavePath.replace("-Data\\", "-Data") + "\\temp\\");
        //3.给文件重新设置一个名字
        String fn = file.getOriginalFilename();
        String suffix;
        if (fn.endsWith(".nii.gz"))
            suffix = ".nii.gz";
        else
            suffix = fn.substring(file.getOriginalFilename().lastIndexOf("."));//后缀
        log.info("文件后缀：{}", suffix);
        String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        //4.创建这个新文件

        File newFile = new File(fileSavePath + "temp\\" + newFileName);
        //5.复制操作
        try {
            file.transferTo(newFile);

        } catch (IOException e) {
            return ResponseResult.failure(e.getMessage());
        }
        String outputPath = fileSavePath + "mark\\" + newFileName;
        String res = dataProcessingService.merge(newFile.getAbsolutePath(), taskItem.path, outputPath);
        if (res.equals("ok")) {
            FileUtils.deleteQuietly(newFile);
            com.example._3dsmarthealthcare.pojo.entity.File outputFile = new com.example._3dsmarthealthcare.pojo.entity.File();
            outputFile.path = outputPath;
            outputFile.uid = Long.parseLong(UserIdThreadLocal.get());
            outputFile.name = newFileName;
            outputFile.pid = taskItem.pid;
            outputFile.uploadTime = new Date();
            outputFile.type = FileUtil.mark;
            outputFile.url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/mark/" + newFileName;
            fileService.save(outputFile);
            return ResponseResult.success(res, outputFile);
        }
        FileUtils.deleteQuietly(newFile);
        return ResponseResult.failure(res);
    }

    @PostMapping("/formda")
    public ResponseResult<?> formda(@RequestBody HashMap<String, Object> dataMap) {
        Integer taskId = (Integer) dataMap.get("taskId");
        Integer taskItemId = (Integer) dataMap.get("taskItemId");
        if (taskItemId == null || taskId == null)
            return ResponseResult.failure("taskId或taskItemId为空");
        Task task = taskService.findTaskById(Long.parseLong(String.valueOf(taskId)));
        if (task == null)
            return ResponseResult.failure("不存在taskId为" + taskId + "的任务");
        if (task.isReasoning == 0)
            return ResponseResult.failure("taskId为" + taskId + "的任务还没有推理，请先推理");
        TaskItem taskItem = taskItemService.getTaskItemById(Long.parseLong(String.valueOf(taskItemId)));
        if (taskItem == null)
            return ResponseResult.failure("不存在taskItemId为" + taskId + "的.inn.gz");
        String res = dataProcessingService.forda(taskItem.path);
        FormdaDTO formdaDTO=new Gson().fromJson(res, FormdaDTO.class);
        return ResponseResult.success("ok", formdaDTO);
    }
}
