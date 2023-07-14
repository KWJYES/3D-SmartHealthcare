package com.example._3dsmarthealthcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.mapper.PatientMapper;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.File;
import com.example._3dsmarthealthcare.pojo.entity.Patient;
import com.example._3dsmarthealthcare.service.PatientService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {

    @Override
    public ResponseResult<?> delete(long id, long uid) {
        LambdaQueryWrapper<Patient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Patient::getId, id).eq(Patient::getUid, uid);
        baseMapper.delete(queryWrapper);
        return ResponseResult.success();
    }

    @Override
    public List<Patient> getAll(long uid) {
        LambdaQueryWrapper<Patient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Patient::getUid, uid);
        return baseMapper.selectList(queryWrapper);
    }
}
