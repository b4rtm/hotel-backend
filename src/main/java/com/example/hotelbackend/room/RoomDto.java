package com.example.hotelbackend.room;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomDto {
    private Long id;
    private String name;
    private int capacity;
    private int pricePerNight;
    private String imagePath;
}
