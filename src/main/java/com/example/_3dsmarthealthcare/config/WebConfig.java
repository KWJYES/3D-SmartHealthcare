package com.example._3dsmarthealthcare.config;

import com.example._3dsmarthealthcare.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 图片保存路径，自动从yml文件中获取数据
     * 示例： E:/inn/
     */
    @Value("${file-save-path}")
    private String fileSavePath;

//    @Autowired
//    private LoginInterceptor loginInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**
         * 配置资源映射
         * 意思是：如果访问的资源路径是以“/inn/”开头的，
         * 就给我映射到本机的“E:/inn/”这个文件夹内，去找你要的资源
         * 注意：E:/inn/ 后面的 “/”一定要带上
         */
        registry.addResourceHandler("/inn/**").addResourceLocations("file:" + fileSavePath);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/signUp","/user/login", "/mail/getCaptcha");

    }
}
