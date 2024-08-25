package com.example.hotelbackend.booking.dto;

import java.time.LocalDate;

public record BookingDateDto(LocalDate checkInDate, LocalDate checkOutDate) {}
