package com.example.hotelbackend.customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    @Email
    private String email;
    private String pesel;
    private String address;
    private String city;
    private String postCode;
    private String password;
    private String phoneNumber;
//    @OneToMany(mappedBy = "customer")
//    private List<Booking> bookings;

}
