package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.File;
import com.example._3dsmarthealthcare.pojo.entity.MaskItem;
import com.example._3dsmarthealthcare.pojo.entity.Patient;
import com.example._3dsmarthealthcare.pojo.entity.TaskItem;
import com.example._3dsmarthealthcare.service.FileService;
import com.example._3dsmarthealthcare.service.MaskItemService;
import com.example._3dsmarthealthcare.service.PatientService;
import com.example._3dsmarthealthcare.service.TaskItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private FileService fileService;
    @Autowired
    private TaskItemService taskItemService;
    @Autowired
    private MaskItemService maskItemService;

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

    /**
     * {
     *     "unreasoningNiiIds":[1,2],
     *     "reasonedNiiIds":[1,2],
     *     "maskNiiIds":[1,2],
     *     "markNiiIds":[1,2],
     *     "patientId":1
     * }
     * @param dataMap
     * @return
     */
    @PostMapping("/appendNii")
    public ResponseResult<?> appendNii(@RequestBody HashMap<String, Object> dataMap) {

        List<Integer> unreasoningNiiIds = (List<Integer>) dataMap.getOrDefault("unreasoningNiiIds", new ArrayList<Integer>());
        List<Integer> reasonedNiiIds = (List<Integer>) dataMap.getOrDefault("reasonedNiiIds", new ArrayList<Integer>());
        List<Integer> maskNiiIds = (List<Integer>) dataMap.getOrDefault("maskNiiIds", new ArrayList<Integer>());
        List<Integer> markNiiIds = (List<Integer>) dataMap.getOrDefault("markNiiIds", new ArrayList<Integer>());
        Integer pid=(Integer) dataMap.get("patientId");
        if (pid==null)
            return ResponseResult.failure("patientId is null,请检查请求体参数");
        HashMap<String,Object> hashMap=new HashMap<>();
        if (unreasoningNiiIds.size() != 0) {
            List<File> files = fileService.findFileByIds(unreasoningNiiIds);
            files.forEach(file -> file.pid=pid);
            fileService.updateBatchById(files);
            hashMap.put("unreasoningNii",files);
        }
        if (reasonedNiiIds.size()!=0){
            List<TaskItem> taskItems=taskItemService.findTaskItemByIds(reasonedNiiIds);
            taskItems.forEach(taskItem -> taskItem.pid=pid);
            taskItemService.updateBatchById(taskItems);
            hashMap.put("reasonedNii",taskItems);
        }
        if (maskNiiIds.size()!=0){
            List<MaskItem> maskItems=maskItemService.findMaskItemByIds(maskNiiIds);
            maskItems.forEach(maskItem -> maskItem.pid=pid);
            maskItemService.updateBatchById(maskItems);
            hashMap.put("maskNii",maskItems);
        }
        if (markNiiIds.size()!=0){
//            hashMap.put("markNii",files);
        }
        return ResponseResult.success("ok",hashMap);
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
