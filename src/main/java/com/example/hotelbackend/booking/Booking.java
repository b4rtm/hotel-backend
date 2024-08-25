package com.example.hotelbackend.booking;

import com.example.hotelbackend.customer.Customer;
import com.example.hotelbackend.review.Review;
import com.example.hotelbackend.room.Room;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Room room;

    private int bookingPrice;

    private boolean isApproved = false;

    @OneToOne
    private Review review;
}
