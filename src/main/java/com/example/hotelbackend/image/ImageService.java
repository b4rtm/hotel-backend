package com.example.hotelbackend.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Service
public class ImageService {

    public static final String URL = "http://localhost:8080/";
    public static String IMAGES_PATH = "src/main/resources/static/images/";

    public String saveFile(MultipartFile image, String name) throws IOException {
        String ext = Objects.requireNonNull(image.getOriginalFilename()).substring(image.getOriginalFilename().lastIndexOf("."));
        name = name.toLowerCase().replaceAll("[ /]", "-") + ext;
        File uploadedFile = new File(IMAGES_PATH + name);
        Files.write(uploadedFile.toPath(), image.getBytes());
        return URL + "images/" + name;
    }
}
