package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.dto.TaskItemDTO;
import com.example._3dsmarthealthcare.pojo.entity.Task;
import com.example._3dsmarthealthcare.service.ReasoningService;
import com.example._3dsmarthealthcare.service.TaskItemService;
import com.example._3dsmarthealthcare.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reasoning")
public class ReasoningController {
    @Autowired
    private ReasoningService reasoningService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskItemService taskItemService;
    @PostMapping("/doTask")
    public ResponseResult<?> doTask(@RequestParam long taskId){
        Task task=taskService.findTaskById(taskId);
        if (task==null)
            return ResponseResult.failure("任务不存在");
        else if (task.isReasoning==1)
            return ResponseResult.failure("任务已完成，请不要重复推理");
        String flaskRes=reasoningService.reasoning(String.valueOf(taskId));
        if (flaskRes.equals("ok")){
            taskService.finishedTask(taskId);
            List<TaskItemDTO> taskItemDTOS= taskItemService.updateFinishedPath(taskId);
            return ResponseResult.success(flaskRes,taskItemDTOS);
        }else {
            return ResponseResult.failure(flaskRes);
        }
    }
}
