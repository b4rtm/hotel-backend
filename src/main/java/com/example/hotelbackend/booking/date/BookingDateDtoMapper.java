package com.example.hotelbackend.booking.date;

import com.example.hotelbackend.booking.Booking;
import org.springframework.stereotype.Service;

@Service
public class BookingDateDtoMapper {

    public BookingDateDto map(Booking booking){
        BookingDateDto bookingDateDto = new BookingDateDto();
        bookingDateDto.setCheckOutDate(booking.getCheckOutDate());
        bookingDateDto.setCheckInDate(booking.getCheckInDate());
        return bookingDateDto;
    }
}
