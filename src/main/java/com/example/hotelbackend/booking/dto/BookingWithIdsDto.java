package com.example.hotelbackend.booking.dto;

import java.time.LocalDate;

public record BookingWithIdsDto(
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Long customerId,
        Long roomId
) {}
