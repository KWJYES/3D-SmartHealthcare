package com.example._3dsmarthealthcare.service;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.Task;

import java.util.List;

public interface TaskService {
    ResponseResult<?> create(String taskName);
    ResponseResult<?> delete(long taskId);

    void finishedTask(long taskId);

    Task findTaskById(long taskId);

    ResponseResult<?> getTasksByUid();
}
