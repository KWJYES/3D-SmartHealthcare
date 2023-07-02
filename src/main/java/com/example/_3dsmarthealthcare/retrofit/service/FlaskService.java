package com.example._3dsmarthealthcare.retrofit.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FlaskService {
    @GET("/hello")
    Call<ResponseBody> hello();
}
