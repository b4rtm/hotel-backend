package com.example.hotelbackend.room;

import com.example.hotelbackend.booking.BookingRepository;
import com.example.hotelbackend.booking.dto.BookingDateDto;
import com.example.hotelbackend.booking.mapper.BookingDateDtoMapper;
import com.example.hotelbackend.image.ImageService;
import com.example.hotelbackend.review.dto.ReviewDto;
import com.example.hotelbackend.review.ReviewDtoMapper;
import com.example.hotelbackend.review.ReviewService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomDtoMapper roomDtoMapper;
    private final ImageService imageService;
    private final BookingRepository bookingRepository;
    private final BookingDateDtoMapper bookingDateDtoMapper;
    private final ReviewService reviewService;
    private final ReviewDtoMapper reviewDtoMapper;

    public RoomService(RoomRepository roomRepository, @Lazy RoomDtoMapper roomDtoMapper, ImageService imageService, BookingRepository bookingRepository, BookingDateDtoMapper bookingDateDtoMapper, ReviewService reviewService, ReviewDtoMapper reviewDtoMapper) {
        this.roomRepository = roomRepository;
        this.roomDtoMapper = roomDtoMapper;
        this.imageService = imageService;
        this.bookingRepository = bookingRepository;
        this.bookingDateDtoMapper = bookingDateDtoMapper;
        this.reviewService = reviewService;
        this.reviewDtoMapper = reviewDtoMapper;
    }

    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream().map(room -> {
            RoomDto roomDto = roomDtoMapper.map(room);
            List<ReviewDto> reviews = reviewService.getReviewsByRoomId(room.getId()).stream()
                    .map(reviewDtoMapper::map)
                    .collect(Collectors.toList());
            roomDto.setReviews(reviews);
            return roomDto;
        }).collect(Collectors.toList());
    }

    public Optional<RoomDto> getRoomById(Long id) {
        List<ReviewDto> reviewsByRoomId = reviewService.getReviewsByRoomId(id).stream().map(reviewDtoMapper::map).toList();

        Optional<RoomDto> roomDto = roomRepository.findById(id)
                .map(roomDtoMapper::map);
        roomDto.ifPresent(roomDto1 -> roomDto1.setReviews(reviewsByRoomId));
        return roomDto;
    }

    public RoomDto saveRoom(RoomDto roomDto, List<MultipartFile> newImages){
        Room room = roomDtoMapper.map(roomDto);
        Room savedRoom = roomRepository.save(room);
        newImages.forEach(image -> imageService.saveFile(image, room.getName(), savedRoom.getId()));
        return roomDtoMapper.map(savedRoom);
    }

    public RoomDto replaceRoom(Long roomId, String name, int capacity, int pricePerNight, String description, String descriptionEn, List<MultipartFile> newImages){
        RoomDto roomDto = new RoomDto();
        roomDto.setName(name);
        roomDto.setCapacity(capacity);
        roomDto.setPricePerNight(pricePerNight);
        roomDto.setDescription(description);
        roomDto.setDescriptionEn(descriptionEn);
        if(newImages != null)
            newImages.forEach(image -> imageService.saveFile(image, name, roomId));

        Room room = roomDtoMapper.map(roomDto);
        room.setId(roomId);
        Room updatedRoom = roomRepository.save(room);
        return roomDtoMapper.map(updatedRoom);
    }

    public List<BookingDateDto> getBookingsDateForRoom(Long id){
        return bookingRepository.findByRoomId(id).stream().map(bookingDateDtoMapper::map).toList();
    }

    void deleteAllRoomImages(Long id){
        List<String> allPaths = imageService.getAllByRoomId(id);
        allPaths.forEach(path -> imageService.deleteRoomImage(id,path));
    }

    public void deleteRoom(Long id){
        deleteAllRoomImages(id);
        roomRepository.deleteById(id);
    }
}
