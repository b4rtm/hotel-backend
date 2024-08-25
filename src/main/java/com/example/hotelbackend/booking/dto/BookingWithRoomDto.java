package com.example.hotelbackend.booking.dto;

import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.room.RoomDto;

import java.time.LocalDate;

public record BookingWithRoomDto(Long id, LocalDate checkInDate, LocalDate checkOutDate, Customer customer,
                                 RoomDto room, boolean hasReview, boolean isApproved) {

}
