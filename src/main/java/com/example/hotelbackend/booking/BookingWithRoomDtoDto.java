package com.example.hotelbackend.booking;

import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.room.RoomDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingWithRoomDtoDto {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Customer customer;
    private RoomDto room;
}
