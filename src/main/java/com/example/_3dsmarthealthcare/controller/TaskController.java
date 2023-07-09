package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.service.TaskItemService;
import com.example._3dsmarthealthcare.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reasoningTask")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskItemService taskItemService;
    @PostMapping("/create")
    public ResponseResult<?> createTask(@RequestParam String name){
        return taskService.create(name);
    }
    @PostMapping("/delete")
    public ResponseResult<?> deleteTask(@RequestParam long id){
        return taskService.delete(id);
    }
    @PostMapping("/addInns")
    public ResponseResult<?> addInnsToTask(@RequestBody HashMap<String,Object> dataMap){
        Long taskId= (Long) dataMap.get("taskId");
        List<Long> fileIds= (List<Long>) dataMap.get("fileIds");
        if (taskId==null||fileIds==null) return ResponseResult.failure("请求数据格式错误");
        return taskItemService.appendItems(taskId,fileIds);
    }
    @PostMapping("/removeInns")
    public ResponseResult<?> removeInnsFromTask(@RequestBody HashMap<String,Object> dataMap){
        Long taskId= (Long) dataMap.get("taskId");
        List<Long> fileIds= (List<Long>) dataMap.get("fileIds");
        if (taskId==null||fileIds==null) return ResponseResult.failure("请求数据格式错误");
        return taskItemService.removeItems(taskId,fileIds);
    }
}
