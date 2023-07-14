package com.example._3dsmarthealthcare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example._3dsmarthealthcare.pojo.entity.Patient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientMapper extends BaseMapper<Patient> {
}
