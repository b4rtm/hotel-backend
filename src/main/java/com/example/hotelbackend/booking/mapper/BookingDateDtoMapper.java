package com.example.hotelbackend.booking.mapper;

import com.example.hotelbackend.booking.Booking;
import com.example.hotelbackend.booking.dto.BookingDateDto;
import org.springframework.stereotype.Service;

@Service
public class BookingDateDtoMapper {

    public BookingDateDto map(Booking booking) {
        return new BookingDateDto(booking.getCheckInDate(), booking.getCheckOutDate());
    }
}
