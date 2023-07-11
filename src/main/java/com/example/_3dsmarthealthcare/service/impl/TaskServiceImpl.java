package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.mapper.TaskMapper;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.File;
import com.example._3dsmarthealthcare.pojo.entity.Task;
import com.example._3dsmarthealthcare.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    @Override
    public ResponseResult<?> create(String taskName) {
        Task task = new Task();
        task.name = taskName;
        task.uid = Long.parseLong(UserIdThreadLocal.get());
        task.createTime = new Date();
        task.isReasoning = 0;
        save(task);
        return ResponseResult.success("ok");
    }

    @Override
    public ResponseResult<?> delete(long taskId) {
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getId, taskId).eq(Task::getUid, Long.parseLong(UserIdThreadLocal.get()));
        baseMapper.delete(queryWrapper);
        return ResponseResult.success();
    }

    @Override
    public void finishedTask(long taskId) {
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getId, taskId).eq(Task::getUid, Long.parseLong(UserIdThreadLocal.get()));
        Task task=getOne(queryWrapper);
        task.isReasoning=1;
        saveOrUpdate(task);
    }

    @Override
    public Task findTaskById(long taskId) {
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getId, taskId).eq(Task::getUid, Long.parseLong(UserIdThreadLocal.get()));
        return getOne(queryWrapper);
    }

    @Override
    public ResponseResult<?> getTasksByUid() {
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Task::getUid, Long.parseLong(UserIdThreadLocal.get()));
        List<Task> tasks = baseMapper.selectList(queryWrapper);
        return ResponseResult.success("ok",tasks);
    }

}
