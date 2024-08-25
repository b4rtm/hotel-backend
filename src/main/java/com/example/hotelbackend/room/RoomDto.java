package com.example.hotelbackend.room;

import com.example.hotelbackend.booking.dto.BookingDateDto;
import com.example.hotelbackend.review.ReviewDto;
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
    private List<String> imagePaths;
    private String description;
    private List<BookingDateDto> bookings;
    private List<ReviewDto> reviews;


    public RoomDto(String name, int capacity, int pricePerNight, String description) {
        this.name = name;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.description = description;
    }
}
