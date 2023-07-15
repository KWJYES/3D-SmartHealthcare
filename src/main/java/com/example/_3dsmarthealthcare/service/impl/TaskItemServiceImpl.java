package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.common.util.FileUtil;
import com.example._3dsmarthealthcare.common.util.RedisUtil;
import com.example._3dsmarthealthcare.mapper.TaskItemMapper;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.dto.TaskItemDTO;
import com.example._3dsmarthealthcare.pojo.entity.File;
import com.example._3dsmarthealthcare.pojo.entity.TaskItem;
import com.example._3dsmarthealthcare.service.TaskItemService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TaskItemServiceImpl extends ServiceImpl<TaskItemMapper, TaskItem> implements TaskItemService {
    @Value(("${file-save-path}"))
    private String fileSavePath;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResponseResult<?> appendItems(long taskId, List<File> files, HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        List<TaskItemDTO> taskItems=new ArrayList<>();
        for (File file : files) {
            TaskItem taskItem = new TaskItem();
            taskItem.path = fileSavePath + "task\\" + UserIdThreadLocal.get() + "\\" + taskId + "\\" + file.name;
            taskItem.url = baseUrl + "/task/" + UserIdThreadLocal.get() + "/" + taskId + "/" + file.name;
            taskItem.name = file.name;
            taskItem.appendTime = new Date();
            taskItem.uid = Long.parseLong(UserIdThreadLocal.get());
            taskItem.taskId = taskId;
            save(taskItem);
            taskItems.add(new TaskItemDTO(taskItem.id,taskItem.url,taskItem.name,taskItem.appendTime,taskItem.uid,taskItem.taskId,taskItem.pid));
            try {
                FileUtils.copyFile(new java.io.File(file.path), new java.io.File(taskItem.path));
            } catch (IOException e) {
                return ResponseResult.failure(e.getMessage());
            }
        }
        return ResponseResult.success("ok",taskItems);
    }

    @Override
    public ResponseResult<?> removeItems(long taskId, List<Integer> itemId) {
        String uid = UserIdThreadLocal.get();
        for (Integer id : itemId) {
            LambdaQueryWrapper<TaskItem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TaskItem::getId, Long.parseLong(String.valueOf(id))).eq(TaskItem::getUid, Long.parseLong(uid)).eq(TaskItem::getTaskId, taskId);
            TaskItem taskItem = getOne(queryWrapper);
            FileUtils.deleteQuietly(new java.io.File(taskItem.path));
            baseMapper.delete(queryWrapper);
        }
        return ResponseResult.success();
    }

    @Override
    public List<TaskItemDTO> updateFinishedPath(long taskId) {
        LambdaQueryWrapper<TaskItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskItem::getTaskId, taskId).eq(TaskItem::getUid, Long.parseLong(UserIdThreadLocal.get()));
        List<TaskItem> taskItems = baseMapper.selectList(queryWrapper);
        List<TaskItem> resList = new ArrayList<>();
        for (TaskItem taskItem : taskItems) {
            String srcPath = taskItem.path;
            taskItem.path = taskItem.path.replace("\\task\\", "\\task_done\\");
//            taskItem.path = taskItem.path.replace(".nii", ".nii.gz");
            taskItem.url = taskItem.url.replace("/task/", "/task_done/");
//            taskItem.url = taskItem.url.replace(".nii", ".nii.gz");
//            taskItem.name = taskItem.name.replace(".nii", ".nii.gz");

            FileUtils.deleteQuietly(new java.io.File(srcPath));//删除原来的文件

            resList.add(taskItem);
        }
        updateBatchById(resList);
        List<TaskItemDTO> taskItemDTOS = new ArrayList<>();
        for (TaskItem taskItem : resList) {
            taskItemDTOS.add(new TaskItemDTO(taskItem.id, taskItem.url, taskItem.name, taskItem.appendTime, taskItem.uid, taskItem.taskId, taskItem.pid));
        }
        return taskItemDTOS;
    }

    @Override
    public ResponseResult<?> getTaskItemsDtoByTaskId(long taskId, HttpServletRequest request) {
        LambdaQueryWrapper<TaskItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskItem::getTaskId, taskId).eq(TaskItem::getUid, Long.parseLong(UserIdThreadLocal.get()));
        List<TaskItem> taskItems = baseMapper.selectList(queryWrapper);
        List<TaskItemDTO> taskItemDTOS = new ArrayList<>();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/file";
        for (TaskItem taskItem : taskItems) {
            String dynamicUrl = fileUtil.getDynamicUrl(UserIdThreadLocal.get(), taskItem.name.substring(taskItem.name.lastIndexOf(".")));
            redisUtil.set(dynamicUrl, taskItem.url, 2, TimeUnit.HOURS);
            taskItemDTOS.add(new TaskItemDTO(taskItem.id, baseUrl + "/" + dynamicUrl, taskItem.name, taskItem.appendTime, taskItem.uid, taskItem.taskId, taskItem.pid));
        }
        return ResponseResult.success("ok", taskItemDTOS);
    }

    @Override
    public TaskItem getTaskItemById(long id) {
        LambdaQueryWrapper<TaskItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TaskItem::getId, id);
        return getOne(queryWrapper);
    }

    @Override
    public List<TaskItem> findTaskItemByIds(List<Integer> reasonedNiiIds) {
        List<TaskItem> taskItems = new ArrayList<>();
        String uid = UserIdThreadLocal.get();
        for (Integer fileId : reasonedNiiIds) {
            Long id = Long.parseLong(String.valueOf(fileId));
            LambdaQueryWrapper<TaskItem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TaskItem::getId, id).eq(TaskItem::getUid, Long.parseLong(uid));
            taskItems.add(getOne(queryWrapper));
        }
        return taskItems;
    }
}
