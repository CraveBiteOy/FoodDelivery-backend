package com.fooddelivery.backend.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fooddelivery.backend.Models.Response.ImagesResponse;
import com.fooddelivery.backend.Models.Response.SingleImageRes;



public interface ImageService {
    byte[] getImageByFileName(String fileName);
    ImagesResponse uploadImages(List<MultipartFile> files); 
    SingleImageRes uploadImage(MultipartFile file);
}
