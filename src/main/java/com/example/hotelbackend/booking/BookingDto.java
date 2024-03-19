package com.example.hotelbackend.booking;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingDto {
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long customerId;
    private Long roomId;
}
