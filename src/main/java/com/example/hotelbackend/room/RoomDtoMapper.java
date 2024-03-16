package com.example.hotelbackend.room;

import org.springframework.stereotype.Service;

@Service
public class RoomDtoMapper {

    RoomDto map(Room room){
        RoomDto roomDto = new RoomDto();
        roomDto.setId(room.getId());
        roomDto.setName(room.getName());
        roomDto.setCapacity(room.getCapacity());
        roomDto.setPricePerNight(room.getPricePerNight());
        roomDto.setImagePath(room.getImagePath());
        return roomDto;
    }
}
