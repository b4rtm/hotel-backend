package com.example.hotelbackend.booking;

import com.example.hotelbackend.room.Room;
import com.example.hotelbackend.customer.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date checkInDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date checkOutDate;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Room room;
    private int bookingPrice;

}
