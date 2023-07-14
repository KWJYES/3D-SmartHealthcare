package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.Patient;
import com.example._3dsmarthealthcare.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/add")
    public ResponseResult<?> addPatient(@RequestBody HashMap<String, Object> dataMap) {
        Patient patient = new Patient();
        patient.age = (int) dataMap.get("age");
        patient.sex = (int) dataMap.get("sex");
        patient.name = (String) dataMap.get("name");
        String time = (String) dataMap.get("birth_date");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            patient.birthDate = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        patient.registrationDate = new Date();
        patient.uid = Long.parseLong(UserIdThreadLocal.get());
        patientService.save(patient);
        return ResponseResult.success("ok", patient);
    }

    @PostMapping("/delete")
    public ResponseResult<?> deletePatient(@RequestBody HashMap<String, Object> dataMap) {
        Integer id = (Integer) dataMap.get("id");
        if (id == null)
            return ResponseResult.failure("patient's id is null");
        return patientService.delete(Long.parseLong(String.valueOf(id)), Long.parseLong(UserIdThreadLocal.get()));
    }

    @PostMapping("/appendNii")
    public ResponseResult<?> appendNii(@RequestBody HashMap<String, Object> dataMap) {
        return ResponseResult.success();
    }

    @PostMapping("/removeNii")
    public ResponseResult<?> removeNii(@RequestBody HashMap<String, Object> dataMap) {
        return ResponseResult.failure();
    }

    @PostMapping("/appendPdf")
    public ResponseResult<?> appendPdf(@RequestBody HashMap<String, Object> dataMap) {
        return ResponseResult.success();
    }

    @PostMapping("/removePdf")
    public ResponseResult<?> removePdf(@RequestBody HashMap<String, Object> dataMap) {
        return ResponseResult.failure();
    }

    @PostMapping("/createPdf")
    public ResponseResult<?> createPdf(@RequestBody HashMap<String, Object> dataMap) {
        return ResponseResult.success();
    }

    @GetMapping("/getAll")
    public ResponseResult<?> getAll() {
        List<Patient> patients = patientService.getAll(Long.parseLong(UserIdThreadLocal.get()));
        return ResponseResult.success("ok", patients);
    }
}
