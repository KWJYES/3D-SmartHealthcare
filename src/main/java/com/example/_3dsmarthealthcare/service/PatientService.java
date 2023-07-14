package com.example._3dsmarthealthcare.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.Patient;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PatientService extends IService<Patient> {
    ResponseResult<?> delete(long id, long uid);

    List<Patient> getAll(long parseLong);
}
