package com.example.hotelbackend.room;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomDtoMapper roomDtoMapper;

    public RoomService(RoomRepository roomRepository, RoomDtoMapper roomDtoMapper) {
        this.roomRepository = roomRepository;
        this.roomDtoMapper = roomDtoMapper;
    }

    List<RoomDto> getRooms(){
        return roomRepository.findAll().stream().map(roomDtoMapper::map).toList();
    }
}
