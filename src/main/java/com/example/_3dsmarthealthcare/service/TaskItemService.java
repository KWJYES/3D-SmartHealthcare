package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.dto.TaskItemDTO;
import com.example._3dsmarthealthcare.pojo.entity.File;
import com.example._3dsmarthealthcare.pojo.entity.TaskItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TaskItemService extends IService<TaskItem> {
    ResponseResult<?> appendItems(long taskId, List<File> fileId, HttpServletRequest request);
    ResponseResult<?> removeItems(long taskId, List<Integer> itemId);

    List<TaskItemDTO> updateFinishedPath(long taskId);

    ResponseResult<?> getTaskItemsDtoByTaskId(long taskId, HttpServletRequest request);

    TaskItem getTaskItemById(long parseLong);

    List<TaskItem> findTaskItemByIds(List<Integer> reasonedNiiIds);

    List<TaskItem> findTaskItemByPid(Long pid);
}
