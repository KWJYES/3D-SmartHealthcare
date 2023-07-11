package com.example._3dsmarthealthcare.service.impl;

import com.example._3dsmarthealthcare.common.UserIdThreadLocal;
import com.example._3dsmarthealthcare.pojo.dto.ResponseResult;
import com.example._3dsmarthealthcare.retrofit.RetrofitTemplate;
import com.example._3dsmarthealthcare.service.ReasoningService;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;

@Service
public class ReasoningServiceImpl implements ReasoningService {
    @Value(("${file-save-path}"))
    private String fileSavePath;
    @Override
    public String reasoning(String taskId) {
        try {
            String loadPath=fileSavePath+"task\\"+UserIdThreadLocal.get()+"\\"+taskId;
            String targetPath=fileSavePath+"task_done\\"+UserIdThreadLocal.get()+"\\"+taskId;
            Response<ResponseBody> response = RetrofitTemplate.getInstance().flaskService.reasoning(loadPath,targetPath).execute();
            if (response.body() == null)
                return "flask response.body is null";
            String str=response.body().string();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
