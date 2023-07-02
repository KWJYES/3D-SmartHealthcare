package com.example._3dsmarthealthcare.service.impl;

import com.example._3dsmarthealthcare.common.DTO.ResponseResult;
import com.example._3dsmarthealthcare.retrofit.RetrofitTemplate;
import com.example._3dsmarthealthcare.service.TestService;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public ResponseResult<?> test() {
        try {
            Response<ResponseBody> response = RetrofitTemplate.getInstance().flaskService.hello().execute();
            String str=response.body().string();
            return ResponseResult.success(str);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.failure(e.getMessage());
        }
    }
}
