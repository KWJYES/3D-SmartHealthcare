package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.mapper.TaskItemMapper;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.TaskItem;
import com.example._3dsmarthealthcare.service.TaskItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskItemServiceImpl extends ServiceImpl<TaskItemMapper, TaskItem> implements TaskItemService {
    @Override
    public ResponseResult<?> appendItems(long taskId, List<Long> fileId) {
        return null;
    }

    @Override
    public ResponseResult<?> removeItems(long taskId, List<Long> itemId) {
        return null;
    }
}
