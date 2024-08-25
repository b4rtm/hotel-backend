package com.example.hotelbackend.booking.dto;

import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.room.Room;

import java.time.LocalDate;

public record BookingDto(
        Long id,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Customer customer,
        Room room,
        boolean isApproved
) {}