package com.example._3dsmarthealthcare.service.impl;

import com.example._3dsmarthealthcare.retrofit.RetrofitTemplate;
import com.example._3dsmarthealthcare.service.DataProcessingService;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
@Service
public class DataProcessingServiceImpl implements DataProcessingService {

    @Override
    public String prepare(String image_path) {
        try {
            Response<ResponseBody> response = RetrofitTemplate.getInstance().flaskService.prepare(image_path).execute();

            if (response.body() == null)
                return "flask response.body is null";
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String mask(String image_path, String output_path) {
        try {
            Response<ResponseBody> response = RetrofitTemplate.getInstance().flaskService.mask(image_path,output_path).execute();
            if (response.body() == null)
                return "flask response.body is null";
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
