package com.example.hotelbackend.room;

import com.example.hotelbackend.image.ImageService;
import org.springframework.stereotype.Service;

@Service
public class RoomDtoMapper {

    private final RoomService roomService;
    private final ImageService imageService;

    public RoomDtoMapper(RoomService roomService, ImageService imageService) {
        this.roomService = roomService;
        this.imageService = imageService;
    }

    public RoomDto map(Room room){
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setPricePerNight(room.getPricePerNight());
        roomDto.setDescription(room.getDescription());
        roomDto.setDescriptionEn(room.getDescriptionEn());
        roomDto.setBookings(roomService.getBookingsDateForRoom(room.getId()));
        roomDto.setImagePaths(imageService.getAllByRoomId(room.getId()));
        return roomDto;
    }

    public Room map(RoomDto dto){
        Room room = new Room();
        room.setName(dto.getName());
        room.setCapacity(dto.getCapacity());
        room.setPricePerNight(dto.getPricePerNight());
        room.setDescription(dto.getDescription());
        room.setDescriptionEn(dto.getDescriptionEn());
        return room;
    }
}
