package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/reasoningTask")
public class TaskController {
    @PostMapping("/create")
    public ResponseResult<?> createTask(@RequestParam String name){

    }
    @PostMapping("/delete")
    public ResponseResult<?> deleteTask(@RequestParam long id){

    }
    @PostMapping("/addInns")
    public ResponseResult<?> addInnsToTask(@RequestBody HashMap<String,Object> dataMap){

    }
    @PostMapping("/removeInns")
    public ResponseResult<?> removeInnsFromTask(@RequestBody HashMap<String,Object> dataMap){

    }
}
