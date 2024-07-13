package com.example.hotelbackend.image;

import com.example.hotelbackend.room.Room;
import com.example.hotelbackend.room.RoomNotFoundException;
import com.example.hotelbackend.room.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ImageService {

    public static final String URL = "http://localhost:8080/";
    public static String IMAGES_PATH = "src/main/resources/static/images/";

    private final ImageRepository imageRepository;
    private final RoomRepository roomRepository;

    public ImageService(ImageRepository imageRepository, RoomRepository roomRepository) {
        this.imageRepository = imageRepository;
        this.roomRepository = roomRepository;
    }

    public Image saveFile(MultipartFile image, String name, Long roomId) throws IOException {
        String ext = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        name = name.toLowerCase().replaceAll("[ /]", "-") + ext;
        File uploadedFile = new File(IMAGES_PATH + name);
        Files.write(uploadedFile.toPath(), image.getBytes());
        String path =  URL + "images/" + name;

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

        Image newImage = new Image();
        newImage.setRoom(room);
        newImage.setPath(path);

        return imageRepository.save(newImage);
    }

    public List<String> getAllByRoomId(Long id){
        return imageRepository.findAllByRoomId(id).stream().map(Image::getPath).toList();
    }
}
