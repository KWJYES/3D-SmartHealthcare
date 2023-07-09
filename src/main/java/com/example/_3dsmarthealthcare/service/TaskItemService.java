package com.example._3dsmarthealthcare.service;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;

import java.util.List;

public interface TaskItemService {
    ResponseResult<?> appendItems(long taskId, List<Long> fileId);
    ResponseResult<?> removeItems(long taskId, List<Long> itemId);
}
