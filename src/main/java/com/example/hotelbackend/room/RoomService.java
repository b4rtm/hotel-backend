package com.example.hotelbackend.room;

import com.example.hotelbackend.booking.BookingService;
import com.example.hotelbackend.booking.date.BookingDateDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomDtoMapper roomDtoMapper;
    private final BookingService bookingService;

    public RoomService(RoomRepository roomRepository, RoomDtoMapper roomDtoMapper, BookingService bookingService) {
        this.roomRepository = roomRepository;
        this.roomDtoMapper = roomDtoMapper;
        this.bookingService = bookingService;
    }

    List<RoomDto> getRooms(){
        return roomRepository.findAll().stream().map(roomDtoMapper::map).toList();
    }

    Optional<RoomDto> getRoomById(Long id) {
        return roomRepository.findById(id)
                .map(roomDtoMapper::map)
                .flatMap(roomDto -> {
                    List<BookingDateDto> bookings = bookingService.getBookingsDateForRoom(id);
                    roomDto.setBookings(bookings);
                    return Optional.of(roomDto);
                });
    }

    RoomDto saveRoom(RoomDto roomDto){
        Room savedRoom = roomRepository.save(roomDtoMapper.map(roomDto));
        return roomDtoMapper.map(savedRoom);
    }

    RoomDto replaceRoom(Long roomId, RoomDto roomDto){
        Room room = roomDtoMapper.map(roomDto);
        room.setId(roomId);
        Room updatedRoom = roomRepository.save(room);
        return roomDtoMapper.map(updatedRoom);
    }

    void deleteRoom(Long id){
        roomRepository.deleteById(id);
    }
}
