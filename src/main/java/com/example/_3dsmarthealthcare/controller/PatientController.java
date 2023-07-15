package com.example._3dsmarthealthcare.controller;

import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.common.util.FileUtil;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.pojo.entity.File;
import com.example._3dsmarthealthcare.pojo.entity.MaskItem;
import com.example._3dsmarthealthcare.pojo.entity.Patient;
import com.example._3dsmarthealthcare.pojo.entity.TaskItem;
import com.example._3dsmarthealthcare.service.FileService;
import com.example._3dsmarthealthcare.service.MaskItemService;
import com.example._3dsmarthealthcare.service.PatientService;
import com.example._3dsmarthealthcare.service.TaskItemService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.util.ResourceUtil;
import com.itextpdf.io.util.StreamUtil;
import com.itextpdf.layout.font.FontProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    @Value(("${file-save-path}"))
    private String fileSavePath;

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
     * "unreasoningNiiIds":[1,2],
     * "reasonedNiiIds":[1,2],
     * "maskNiiIds":[1,2],
     * "markNiiIds":[1,2],
     * "patientId":1
     * }
     *
     * @param dataMap
     * @return
     */
    @PostMapping("/appendNii")
    public ResponseResult<?> appendNii(@RequestBody HashMap<String, Object> dataMap) {

        List<Integer> unreasoningNiiIds = (List<Integer>) dataMap.getOrDefault("unreasoningNiiIds", new ArrayList<Integer>());
        List<Integer> reasonedNiiIds = (List<Integer>) dataMap.getOrDefault("reasonedNiiIds", new ArrayList<Integer>());
        List<Integer> maskNiiIds = (List<Integer>) dataMap.getOrDefault("maskNiiIds", new ArrayList<Integer>());
        List<Integer> markNiiIds = (List<Integer>) dataMap.getOrDefault("markNiiIds", new ArrayList<Integer>());
        Integer pid = (Integer) dataMap.get("patientId");
        if (pid == null)
            return ResponseResult.failure("patientId is null,请检查请求体参数");
        HashMap<String, Object> hashMap = new HashMap<>();
        if (unreasoningNiiIds.size() != 0) {
            List<File> files = fileService.findFileByIds(unreasoningNiiIds);
            files.forEach(file -> file.pid = pid);
            fileService.updateBatchById(files);
            hashMap.put("unreasoningNii", files);
        }
        if (reasonedNiiIds.size() != 0) {
            List<TaskItem> taskItems = taskItemService.findTaskItemByIds(reasonedNiiIds);
            taskItems.forEach(taskItem -> taskItem.pid = pid);
            taskItemService.updateBatchById(taskItems);
            hashMap.put("reasonedNii", taskItems);
        }
        if (maskNiiIds.size() != 0) {
            List<MaskItem> maskItems = maskItemService.findMaskItemByIds(maskNiiIds);
            maskItems.forEach(maskItem -> maskItem.pid = pid);
            maskItemService.updateBatchById(maskItems);
            hashMap.put("maskNii", maskItems);
        }
        if (markNiiIds.size() != 0) {
//            hashMap.put("markNii",files);
        }
        return ResponseResult.success("ok", hashMap);
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

    @Autowired
    private TemplateEngine templateEngine;

    @PostMapping("/createPdf")
    public ResponseResult<?> createPdf(@RequestBody HashMap<String, Object> dataMap, HttpServletRequest request) {
        Integer pid = (Integer) dataMap.get("pid");
        if (pid == null)
            return ResponseResult.failure("pid is null");
        Context context = new Context();
        String patient_name = (String) dataMap.get("patient_name");
        context.setVariable("patient_name", patient_name);
        String patient_gender = (String) dataMap.get("patient_gender");
        context.setVariable("patient_gender", patient_gender);
        Integer patient_age = (Integer) dataMap.get("patient_age");
        context.setVariable("patient_age", patient_age);
        String patient_phone = (String) dataMap.get("patient_phone");
        context.setVariable("patient_phone", patient_phone);
        String patient_address = (String) dataMap.get("patient_address");
        context.setVariable("patient_address", patient_address);
        String visit_date = (String) dataMap.get("visit_date");
        context.setVariable("visit_date", visit_date);
        String diagnosis = (String) dataMap.get("diagnosis");
        context.setVariable("diagnosis", diagnosis);
        String doctor_advice = (String) dataMap.get("doctor_advice");
        context.setVariable("doctor_advice", doctor_advice);
        String html = templateEngine.process("case_sheet", context);
        File file = new File();
        file.uid = Long.parseLong(UserIdThreadLocal.get());
        file.name = patient_name + "_病例本u" + file.uid + System.currentTimeMillis();
        file.type = FileUtil.pdf;
        file.pid = Long.parseLong(String.valueOf(pid));
        file.uploadTime = new Date();
        file.path = fileSavePath + "pdf\\userId_" + file.uid + "\\" + file.name + ".pdf";
        //协议 :// ip地址 ：端口号 / 文件目录(/fileType/userId/xxx.nii)
        file.url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/pdf/userId_" + file.uid + "/" + file.name + ".pdf";
        java.io.File f=new java.io.File(file.path);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        try (FileOutputStream outputStream = new FileOutputStream(file.path)) {

            //替换掉文档里面的转义字符，工具无法转换转义符，页面会乱码，所以不建议直接通过流转换成PDF，而是先读到内存里替换掉转义符再转
            html = html.replace("&nbsp;", " ");
            html = html.replace("&ndash;", "–");
            html = html.replace("&mdash;", "—");
            html = html.replace("&lsquo;", "‘");
            html = html.replace("&rsquo;", "’");
            html = html.replace("&sbquo;", "‚");
            html = html.replace("&ldquo;", "“");
            html = html.replace("&rdquo;", "”");
            html = html.replace("&bdquo;", "„");
            html = html.replace("&prime;", "′");
            html = html.replace("&Prime;", "″");
            html = html.replace("&lsaquo;", "‹");
            html = html.replace("&rsaquo;", "›");
            html = html.replace("&oline;", "‾");

            ConverterProperties converterProperties = new ConverterProperties();
            FontProvider provider = new FontProvider();

            //这里是为了解决中文无法转换的问题，自己将黑体的字体包放到对应目录读取并载入，用的时候可以视情况替换路径（我这边是直接塞到引入的jar包里面，读取的class文件目录）
            InputStream stream = ResourceUtil.getResourceStream("font/simhei.ttf");
            byte[] fontProgramBytes = StreamUtil.inputStreamToArray(stream);
            provider.addFont(fontProgramBytes);
            converterProperties.setFontProvider(provider);
            HtmlConverter.convertToPdf(html, outputStream, converterProperties);
            fileService.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseResult.success("ok", file);
    }

    @GetMapping("/getRecordBook")
    public ResponseResult<?> getPdf(@RequestParam int pid) {
        Long id=Long.parseLong(String.valueOf(pid));
        return fileService.findPdfByPid(pid);
    }

    @GetMapping("/getAll")
    public ResponseResult<?> getAll() {
        List<Patient> patients = patientService.getAll(Long.parseLong(UserIdThreadLocal.get()));
        return ResponseResult.success("ok", patients);
    }

    @GetMapping("/getNii")
    public ResponseResult<?> getNii(@RequestParam int patientId) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Long pid = Long.parseLong(String.valueOf(patientId));
        List<File> files = fileService.getNiiByPid(pid);
        hashMap.put("unreasoningNii", files);
        List<TaskItem> taskItems = taskItemService.findTaskItemByPid(pid);
        hashMap.put("reasonedNii", taskItems);
        List<MaskItem> maskItems = maskItemService.findMaskItemByPids(pid);
        hashMap.put("maskNii", maskItems);
        hashMap.put("markNii", null);
        return ResponseResult.success("ok", hashMap);
    }
}
