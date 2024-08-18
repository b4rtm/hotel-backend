package com.example.hotelbackend.booking;

import com.example.hotelbackend.room.RoomDtoMapper;
import org.springframework.stereotype.Service;

@Service
public class BookingWithRoomDtoDtoMapper {

    private final RoomDtoMapper roomDtoMapper;

    public BookingWithRoomDtoDtoMapper(RoomDtoMapper roomDtoMapper) {
        this.roomDtoMapper = roomDtoMapper;
    }

    BookingWithRoomDtoDto map(Booking booking){
        BookingWithRoomDtoDto bookingDto = new BookingWithRoomDtoDto();
        bookingDto.setId(booking.getId());
        bookingDto.setCustomer(booking.getCustomer());
        bookingDto.setRoom(roomDtoMapper.map(booking.getRoom()));
        bookingDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDto.setCheckInDate(booking.getCheckInDate());
        bookingDto.setHasReview(booking.getReview() != null);
        bookingDto.setApproved(booking.isApproved());
        return bookingDto;
    }
}
