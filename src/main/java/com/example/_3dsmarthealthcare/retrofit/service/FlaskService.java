package com.example._3dsmarthealthcare.retrofit.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FlaskService {
    @GET("/hello")
    Call<ResponseBody> hello();
    @POST("/reasoning")
    Call<ResponseBody> reasoning(@Query("loadPath") String loadPath,@Query("targetPath") String targetPath);

    @POST("/prepare")
    Call<ResponseBody> prepare(@Query("image_path") String image_path);

    @POST("/mask")
    Call<ResponseBody> mask(@Query("image_path") String image_path,@Query("output_path") String output_path);
}
