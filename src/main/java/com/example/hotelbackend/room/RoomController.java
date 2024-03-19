package com.example.hotelbackend.room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    ResponseEntity<List<RoomDto>> getRooms(){
        List<RoomDto> rooms = roomService.getRooms();
        if (rooms.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    ResponseEntity<RoomDto> getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
