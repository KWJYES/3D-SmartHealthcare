package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.File;
import com.example._3dsmarthealthcare.service.FileService;
import com.example._3dsmarthealthcare.service.TaskItemService;
import com.example._3dsmarthealthcare.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/reasoningTask")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskItemService taskItemService;
    @Autowired
    private FileService fileService;
    @PostMapping("/create")
    public ResponseResult<?> createTask(@RequestParam String name){
        return taskService.create(name);
    }
    @PostMapping("/delete")
    public ResponseResult<?> deleteTask(@RequestParam long id){
        return taskService.delete(id);
    }
    @PostMapping("/addInns")
    public ResponseResult<?> addInnsToTask(@RequestBody HashMap<String,Object> dataMap, HttpServletRequest request){
        Long taskId= Long.parseLong(String.valueOf(dataMap.get("taskId")));
        List fileIds= (List) dataMap.get("fileIds");
        if (taskId==null||fileIds==null) return ResponseResult.failure("请求数据格式错误");
        List<File> fileList=fileService.findFileByIds(fileIds);
        return taskItemService.appendItems(taskId,fileList,request );
    }
    @PostMapping("/removeInns")
    public ResponseResult<?> removeInnsFromTask(@RequestBody HashMap<String,Object> dataMap){
        Long taskId= Long.parseLong(String.valueOf(dataMap.get("taskId")));
        List fileIds= (List) dataMap.get("fileIds");
        if (taskId==null||fileIds==null) return ResponseResult.failure("请求数据格式错误");
        return taskItemService.removeItems(taskId,fileIds);
    }

    @GetMapping("/getTasks")
    public ResponseResult<?> getTasks(){
        return taskService.getTasksByUid();
    }
    @GetMapping("/getTaskItems")
    public ResponseResult<?> getTaskItems(@RequestParam long id,HttpServletRequest request){
        return taskItemService.getTaskItemsByTaskId(id, request);
    }
}
