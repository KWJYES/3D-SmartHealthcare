package com.example._3dsmarthealthcare.service;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;

import java.util.List;

public interface TaskService {
    ResponseResult<?> create(String taskName);
    ResponseResult<?> delete(long taskId);
}
