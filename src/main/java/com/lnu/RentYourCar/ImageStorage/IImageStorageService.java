package com.lnu.RentYourCar.ImageStorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageStorageService {
    void saveFile(String uploadDir, String photosOfTheCar, MultipartFile multipartFile) throws IOException;
}
