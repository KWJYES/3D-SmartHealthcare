package com.example._3dsmarthealthcare.service;

public interface DataProcessingService{
    String prepare(String image_path);
    String mask(String image_path, String output_path);

    String merge(String absolutePath, String path, String outputPath);

    String forda(String path);
}
