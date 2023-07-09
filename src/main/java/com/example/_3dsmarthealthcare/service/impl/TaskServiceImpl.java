package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.mapper.TaskMapper;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.Task;
import com.example._3dsmarthealthcare.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Override
    public ResponseResult<?> create(String taskName) {
        return null;
    }

    @Override
    public ResponseResult<?> delete(long taskId) {
        return null;
    }

}
