package com.example.hotelbackend.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String pesel;
    private String address;
    private String city;
    private String postCode;
    private String password;
    private String phoneNumber;

}
