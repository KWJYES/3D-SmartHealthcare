package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.mapper.MaskItemMapper;
import com.example._3dsmarthealthcare.pojo.entity.MaskItem;
import com.example._3dsmarthealthcare.pojo.entity.TaskItem;
import com.example._3dsmarthealthcare.service.MaskItemService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaskItemServiceImpl extends ServiceImpl<MaskItemMapper, MaskItem> implements MaskItemService {
    @Override
    public List<MaskItem> findMaskItemByIds(List<Integer> maskNiiIds) {
        List<MaskItem> maskItems = new ArrayList<>();
        String uid = UserIdThreadLocal.get();
        for (Integer fileId : maskNiiIds) {
            Long id = Long.parseLong(String.valueOf(fileId));
            LambdaQueryWrapper<MaskItem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(MaskItem::getId, id).eq(MaskItem::getUid, Long.parseLong(uid));
            maskItems.add(getOne(queryWrapper));
        }
        return maskItems;
    }
}
