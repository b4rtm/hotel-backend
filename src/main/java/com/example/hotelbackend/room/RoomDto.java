package com.example.hotelbackend.room;

import com.example.hotelbackend.booking.date.BookingDateDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomDto {
    private Long id;
    private String name;
    private int capacity;
    private int pricePerNight;
    private String imagePath;
    private String description;
    private List<BookingDateDto> bookings;
}
