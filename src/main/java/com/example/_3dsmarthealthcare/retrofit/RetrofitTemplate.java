package com.example._3dsmarthealthcare.retrofit;

import com.example._3dsmarthealthcare.retrofit.service.FlaskService;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

import java.util.concurrent.TimeUnit;


public class RetrofitTemplate {
    private static volatile RetrofitTemplate retrofitTemplate;
    private static volatile Retrofit retrofit;
    private RetrofitTemplate(){
    }
    private static final OkHttpClient client = new OkHttpClient.Builder().
            connectTimeout(10, TimeUnit.MINUTES).
            readTimeout(10, TimeUnit.MINUTES).
            writeTimeout(10, TimeUnit.MINUTES).build();


    public static RetrofitTemplate getInstance(){
        if (retrofitTemplate==null){
            synchronized (RetrofitTemplate.class){
                if (retrofitTemplate==null){
                    //构建Retrofit实例
                    retrofit = new Retrofit.Builder()
                            //设置网络请求BaseUrl地址
                            .baseUrl("http://127.0.0.1:5001/")
                            //设置数据解析器
                            //.addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();
                    retrofitTemplate=new RetrofitTemplate();

                }
            }
        }
        return retrofitTemplate;
    }
   public FlaskService flaskService=retrofit.create(FlaskService.class);
}
