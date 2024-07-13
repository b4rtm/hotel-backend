package com.example.hotelbackend.room;

import com.example.hotelbackend.booking.BookingService;
import com.example.hotelbackend.image.ImageService;
import org.springframework.stereotype.Service;

@Service
public class RoomDtoMapper {

    private final BookingService bookingService;
    private final ImageService imageService;

    public RoomDtoMapper(BookingService bookingService, ImageService imageService) {
        this.bookingService = bookingService;
        this.imageService = imageService;
    }

    RoomDto map(Room room){
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setPricePerNight(room.getPricePerNight());
        roomDto.setDescription(room.getDescription());
        roomDto.setBookings(bookingService.getBookingsDateForRoom(room.getId()));
        roomDto.setImagePaths(imageService.getAllByRoomId(room.getId()));
        return roomDto;
    }

    Room map(RoomDto dto){
        Room room = new Room();
        room.setName(dto.getName());
        room.setCapacity(dto.getCapacity());
        room.setPricePerNight(dto.getPricePerNight());
        room.setDescription(dto.getDescription());
        return room;
    }
}
