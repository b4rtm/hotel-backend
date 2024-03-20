package com.example.hotelbackend.booking.date;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingDateDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
