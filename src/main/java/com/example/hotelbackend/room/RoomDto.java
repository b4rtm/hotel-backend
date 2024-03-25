package com.example.hotelbackend.room;

import com.example.hotelbackend.booking.date.BookingDateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private String name;
    private int capacity;
    private int pricePerNight;
    private String imagePath;
    private String description;
    private List<BookingDateDto> bookings;



    public RoomDto(String name, int capacity, int pricePerNight, String imagePath, String description) {
        this.name = name;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.imagePath = imagePath;
        this.description = description;
    }
}
