package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.pojo.dto.MaskItemDTO;
import com.example._3dsmarthealthcare.pojo.dto.PrepareDTO;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.MaskItem;
import com.example._3dsmarthealthcare.pojo.entity.Task;
import com.example._3dsmarthealthcare.pojo.entity.TaskItem;
import com.example._3dsmarthealthcare.service.DataProcessingService;
import com.example._3dsmarthealthcare.service.MaskItemService;
import com.example._3dsmarthealthcare.service.TaskItemService;
import com.example._3dsmarthealthcare.service.TaskService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dataProcessing")
public class DataProcessingController {
    @Autowired
    private DataProcessingService dataProcessingService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskItemService taskItemService;
    @Autowired
    private MaskItemService maskItemService;

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
        File dir=new File(out_path).getParentFile();
        if (!dir.exists())
            dir.mkdirs();
        String res = dataProcessingService.mask(image_path, out_path);
        if (res.equals("ok")) {
            MaskItem maskItem = new MaskItem();
            maskItem.name=taskItem.name;
            maskItem.id = taskItem.id;
            maskItem.url = taskItem.url.replace("/task_done/","/task_done/mask/");
            maskItem.path = taskItem.path.replace("\\task_done\\","\\task_done\\mask\\");
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
    public ResponseResult<?> merge(@RequestBody HashMap<String,Object> dataMap){
        return ResponseResult.success();
    }
}
