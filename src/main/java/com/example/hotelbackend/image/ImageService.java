package com.example.hotelbackend.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.hotelbackend.room.Room;
import com.example.hotelbackend.room.RoomNotFoundException;
import com.example.hotelbackend.room.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    private final ImageRepository imageRepository;
    private final RoomRepository roomRepository;
    private final AmazonS3 s3Client;

    public ImageService(ImageRepository imageRepository, RoomRepository roomRepository, AmazonS3 s3Client) {
        this.imageRepository = imageRepository;
        this.roomRepository = roomRepository;
        this.s3Client = s3Client;
    }

    public Image saveFile(MultipartFile image, String name, Long roomId) throws IOException {
        String ext = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
        name = name + "-" + System.currentTimeMillis();
        name = name.toLowerCase().replaceAll("[ /]", "-") + ext;

        s3Client.putObject(new PutObjectRequest(bucketName, name, image.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        String path = s3Client.getUrl(bucketName, name).toString();

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("Room not found with id: " + roomId));

        Image newImage = new Image();
        newImage.setRoom(room);
        newImage.setPath(path);
        return imageRepository.save(newImage);
    }

    public List<String> getAllByRoomId(Long id) {
        return imageRepository.findAllByRoomId(id)
                .stream()
                .map(Image::getPath)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteRoomImage(Long id, String imageUrl) {
        imageRepository.deleteByPathAndRoomId(imageUrl, id);

        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        s3Client.deleteObject(bucketName, fileName);
    }

    public String getImageUrl(String imageName) {
        return s3Client.getUrl(bucketName, imageName).toString();
    }
}
