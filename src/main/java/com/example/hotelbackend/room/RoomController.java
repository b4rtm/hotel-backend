package com.example.hotelbackend.room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping
    ResponseEntity<RoomDto> createRoom(@RequestBody RoomDto room){
        RoomDto savedRoom = roomService.saveRoom(room);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedRoom.getId()).toUri();
        return ResponseEntity.created(uri).body(savedRoom);
    }

    @GetMapping("/{id}")
    ResponseEntity<RoomDto> getRoomById(@PathVariable Long id){
        return roomService.getRoomById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomDto roomDto){
        return ResponseEntity.ok(roomService.replaceRoom(id, roomDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteRoom(@PathVariable Long id){
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
