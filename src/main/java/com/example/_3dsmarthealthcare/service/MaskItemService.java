package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.pojo.entity.MaskItem;

import java.util.List;

public interface MaskItemService extends IService<MaskItem> {
    List<MaskItem> findMaskItemByIds(List<Integer> maskNiiIds);
}
