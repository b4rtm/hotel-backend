package com.example.hotelbackend.room;

import com.example.hotelbackend.image.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final ImageService imageService;

    public RoomController(RoomService roomService, ImageService imageService) {
        this.roomService = roomService;
        this.imageService = imageService;
    }

    @GetMapping
    ResponseEntity<List<RoomDto>> getRooms(){
        List<RoomDto> rooms = roomService.getRooms();
        if (rooms.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(rooms);
    }

    @PostMapping
    ResponseEntity<RoomDto> createRoom(@RequestParam("name") String name,
                                       @RequestParam("capacity") int capacity,
                                       @RequestParam("pricePerNight") int pricePerNight,
                                       @RequestParam("description") String description,
                                       @RequestParam("image") MultipartFile image){
        String filePath = null;
        try {
            filePath = imageService.saveFile(image, name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RoomDto savedRoom = roomService.saveRoom(new RoomDto(name, capacity, pricePerNight, filePath, description));
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
