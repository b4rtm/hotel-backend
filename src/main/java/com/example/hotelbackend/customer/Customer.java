package com.example.hotelbackend.customer;

import com.example.hotelbackend.booking.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String PESEL;
    private String password;
    private String phoneNumber;
    @OneToMany(mappedBy = "customer")
    private List<Booking> bookings;

}
