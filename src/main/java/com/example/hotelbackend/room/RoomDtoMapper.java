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
        roomDto.setDescription(room.getDescription());
        return roomDto;
    }

    Room map(RoomDto dto){
        Room room = new Room();
        room.setName(dto.getName());
        room.setCapacity(dto.getCapacity());
        room.setPricePerNight(dto.getPricePerNight());
        room.setImagePath(dto.getImagePath());
        room.setDescription(dto.getDescription());
        return room;
    }
}
