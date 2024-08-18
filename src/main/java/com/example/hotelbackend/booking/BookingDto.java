package com.example.hotelbackend.booking;

import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.room.Room;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingDto {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Customer customer;
    private Room room;
    private boolean isApproved;
}
