package com.example._3dsmarthealthcare.service;

import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.File;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TaskItemService {
    ResponseResult<?> appendItems(long taskId, List<File> fileId, HttpServletRequest request);
    ResponseResult<?> removeItems(long taskId, List<Integer> itemId);

    void updateFinishedPath(long taskId);

    ResponseResult<?> getTaskItemsByTaskId(long taskId, HttpServletRequest request);
}
