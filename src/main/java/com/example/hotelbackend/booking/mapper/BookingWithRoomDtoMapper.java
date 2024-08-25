package com.example.hotelbackend.booking.mapper;

import com.example.hotelbackend.booking.Booking;
import com.example.hotelbackend.booking.dto.BookingWithRoomDto;
import com.example.hotelbackend.room.RoomDtoMapper;
import org.springframework.stereotype.Service;

@Service
public class BookingWithRoomDtoMapper {

    private final RoomDtoMapper roomDtoMapper;

    public BookingWithRoomDtoMapper(RoomDtoMapper roomDtoMapper) {
        this.roomDtoMapper = roomDtoMapper;
    }

    public BookingWithRoomDto map(Booking booking) {
        return new BookingWithRoomDto(booking.getId(), booking.getCheckInDate(), booking.getCheckOutDate(), booking.getCustomer(), roomDtoMapper.map(booking.getRoom()), booking.getReview() != null, booking.isApproved());
    }
}
