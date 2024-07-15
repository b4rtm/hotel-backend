package com.example.hotelbackend.room;

import com.example.hotelbackend.booking.BookingRepository;
import com.example.hotelbackend.booking.date.BookingDateDto;
import com.example.hotelbackend.booking.date.BookingDateDtoMapper;
import com.example.hotelbackend.image.ImageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomDtoMapper roomDtoMapper;
    private final ImageService imageService;
    private final BookingRepository bookingRepository;
    private final BookingDateDtoMapper bookingDateDtoMapper;


    public RoomService(RoomRepository roomRepository, @Lazy RoomDtoMapper roomDtoMapper, ImageService imageService, BookingRepository bookingRepository, BookingDateDtoMapper bookingDateDtoMapper) {
        this.roomRepository = roomRepository;
        this.roomDtoMapper = roomDtoMapper;
        this.imageService = imageService;
        this.bookingRepository = bookingRepository;
        this.bookingDateDtoMapper = bookingDateDtoMapper;
    }

    List<RoomDto> getRooms(){
        return roomRepository.findAll().stream().map(roomDtoMapper::map).toList();
    }

    public Optional<RoomDto> getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(roomDtoMapper::map);
    }

    RoomDto saveRoom(RoomDto roomDto){
        Room room = roomDtoMapper.map(roomDto);
        Room savedRoom = roomRepository.save(room);
        return roomDtoMapper.map(savedRoom);
    }

    RoomDto replaceRoom(Long roomId, RoomDto roomDto){
        Room room = roomDtoMapper.map(roomDto);
        room.setId(roomId);
//        room.setImagePath(roomRepository.findById(roomId).map(Room::getImagePath).orElse("http://localhost:8080/images/room1.jpg"));
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

    void deleteRoom(Long id){
        deleteAllRoomImages(id);
        roomRepository.deleteById(id);
    }
}
